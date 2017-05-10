/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao;

import com.weavers.duqhan.domain.ProductSizeColorMap;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Android-3
 */
public interface ProductSizeColorMapDao extends BaseDao<ProductSizeColorMap>{
    
    HashMap<Long, ProductSizeColorMap> getSizeColorMapbyMinPriceIfAvailable(List<Long> productIds);
    
    HashMap<Long, ProductSizeColorMap> getSizeColorMapbyMinPriceRecentView(List<Long> productIds);
    
    List<ProductSizeColorMap> getSizeColorMapByProductId(Long productId);
    
    HashMap<Long, ProductSizeColorMap> getSizeColorMapByProductIds(List<Long> productIds);
}
