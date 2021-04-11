package com.liquid.redisson.lock.demo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liquid.redisson.lock.demo.dto.PmsProductSkuDTO;
import com.liquid.redisson.lock.demo.entity.PmsProductSku;
import com.liquid.redisson.lock.demo.mapper.InventoryMapper;
import com.liquid.redisson.lock.demo.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * InventoryServiceImpl.
 *
 * @author Liquid
 */
@Service("inventoryService")
@Slf4j
public class InventoryServiceImpl implements InventoryService {


    private final InventoryMapper inventoryMapper;
    private final RedissonClient redissonClient;
    private final JedisPool jedisPool;

    @Autowired(required = false)
    public InventoryServiceImpl(InventoryMapper inventoryMapper, RedissonClient redissonClient, JedisPool jedisPool) {
        this.inventoryMapper = inventoryMapper;
        this.redissonClient = redissonClient;
        this.jedisPool = jedisPool;
    }

    /**
     * 扣减库存操作
     *
     * @param pmsProductSkuDTO 库存DTO对象
     * @return true
     */
    @Override
    public Boolean decrease(PmsProductSkuDTO pmsProductSkuDTO) {
        //单机部署，可以使用本地锁
        //在集群分布式场景下，如果不加锁会造成超卖的问题
        String lockKey = "decrease_stock_lock" + pmsProductSkuDTO.getProductId();
        String countKey = "count_sold_" + pmsProductSkuDTO.getProductId();

        //根据redis的部署方式(单机，sentinel和cluster),获取对应的redisson client
        //redis单机模式下
        RLock lock = redissonClient.getLock(lockKey);
        try {
            //500ms 获取不到锁默认失败，30秒后锁失效
            boolean tryLock = lock.tryLock(500, 30000, TimeUnit.SECONDS);
            if (tryLock) {
                RAtomicLong stock = redissonClient.getAtomicLong(countKey);
                //计数器小于阈值时允许减库存
                if (stock.get() < 10000) {
                    inventoryMapper.decrease(pmsProductSkuDTO);
                    //实现计数功能
                    stock.addAndGet(pmsProductSkuDTO.getCount());
                } else {
                    throw new RuntimeException("库存不足！");
                }
            }
        } catch (InterruptedException e) {

        } finally {
            //释放锁
            lock.unlock();
        }
        return true;
    }

    /**
     * 使用Jedis封装分布式锁
     *
     * @param pmsProductSkuDTO
     * @return
     */
    public boolean decrease2(PmsProductSkuDTO pmsProductSkuDTO) {
        //针对同一商品，获取相同的key
        String lockKey = "decrease_stock_lock" + pmsProductSkuDTO.getProductId();
        //获取value,保证唯一值(uuid拼接线程id)
        String value = String.valueOf(UUID.randomUUID()) + "-" + Thread.currentThread().getId();

        //根据业务，没有获取到锁是否阻塞
        //本例通过自旋模拟阻塞
        try {
            if (!tryLock(lockKey, value, 30000)) {
                //没有获取到锁，扣减库存失败
                return false;
            }
            //业务上要对剩余库存容量做判断
            inventoryMapper.decrease(pmsProductSkuDTO);
        } finally {
            unLock(lockKey, value);
        }
        return true;
    }

    /**
     * 获取锁
     *
     * @param key
     * @param value
     * @param expireMillionSeconds
     * @return
     */
    private boolean tryLock(String key, String value, int expireMillionSeconds) {
        Jedis jedis = jedisPool.getResource();
        long start = System.currentTimeMillis();
        //也可以采用setnx+lua,如果不使用set key value nx px milliseconds命令，无法保证向redis写值和设置过期时间的原子性，容易造成死锁
        //如果写的值不唯一的话，或造成误解锁的问题
        String res = jedis.set(key, value, "NX", "PX", 30000);
        for (; ; ) {
            if (StrUtil.equals(res, "OK")) {
                //向redis写成功，获取到锁
                //释放资源
                jedis.close();
                return true;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long time = System.currentTimeMillis() - start;
            //10秒内无法获取到锁，返回，此次扣减库存取消
            if (time > 10) {
                jedis.close();
                return false;
            }
        }
    }

    /**
     * 释放锁
     *
     * @param key
     * @param value
     * @return
     */
    private boolean unLock(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        //使用lua脚本保证操作的原子性
        //避免在多线程场景下，发生线程切换，造成误锁的情况。key因为过期，其它节点机器能够或取到锁。相当于直接执行del指令
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object eval = jedis.eval(script, Collections.singletonList(key), Collections.singletonList(value));
        return Long.valueOf(1L).equals(eval);
    }

    /**
     * 获取商品库存信息.
     *
     * @param productId 商品id
     * @return PmsProductSku
     */
    @Override
    public PmsProductSku findByProductId(String productId) {
        QueryWrapper<PmsProductSku> wrapper = new QueryWrapper<>();
        wrapper.eq("productId", productId);
        return inventoryMapper.selectOne(wrapper);
    }

}
