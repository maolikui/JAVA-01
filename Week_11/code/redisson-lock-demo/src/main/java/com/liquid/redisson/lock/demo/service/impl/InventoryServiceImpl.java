package com.liquid.redisson.lock.demo.service.impl;

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

    @Autowired(required = false)
    public InventoryServiceImpl(InventoryMapper inventoryMapper, RedissonClient redissonClient) {
        this.inventoryMapper = inventoryMapper;
        this.redissonClient = redissonClient;
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
