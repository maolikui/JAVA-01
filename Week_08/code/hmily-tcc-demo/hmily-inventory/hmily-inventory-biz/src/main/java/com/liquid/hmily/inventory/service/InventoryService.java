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

package com.liquid.hmily.inventory.service;

import com.liquid.hmily.inventory.api.dto.PmsProductSkuDTO;
import com.liquid.hmily.inventory.api.entity.PmsProductSku;


/**
 * The interface Inventory service.
 *
 * @author Liquid
 */
public interface InventoryService {

    /**
     * 扣减库存操作.
     *
     * @param pmsProductSkuDTO 库存DTO对象
     * @return true boolean
     */
    Boolean decrease(PmsProductSkuDTO pmsProductSkuDTO);

    /**
     * 获取商品库存信息.
     *
     * @param productId 商品id
     * @return PmsProductSku pms product sku
     */
    PmsProductSku findByProductId(String productId);
}
