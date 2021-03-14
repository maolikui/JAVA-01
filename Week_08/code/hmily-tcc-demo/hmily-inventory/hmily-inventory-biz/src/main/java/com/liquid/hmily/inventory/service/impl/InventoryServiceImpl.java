/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liquid.hmily.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liquid.hmily.inventory.api.dto.PmsProductSkuDTO;
import com.liquid.hmily.inventory.api.entity.PmsProductSku;
import com.liquid.hmily.inventory.mapper.InventoryMapper;
import com.liquid.hmily.inventory.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * InventoryServiceImpl.
 *
 * @author xiaoyu
 */
@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final InventoryMapper inventoryMapper;

    @Autowired(required = false)
    public InventoryServiceImpl(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    /**
     * 扣减库存操作
     *
     * @param pmsProductSkuDTO 库存DTO对象
     * @return true
     */
    @Override
    public Boolean decrease(PmsProductSkuDTO pmsProductSkuDTO) {
        LOGGER.info("==========try扣减库存decrease===========");
        inventoryMapper.decrease(pmsProductSkuDTO);
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
