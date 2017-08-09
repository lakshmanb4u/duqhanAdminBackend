/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao;

import com.weavers.duqhan.domain.TempProduct;
import com.weavers.duqhan.domain.Temtproductlinklist;
import java.util.List;

/**
 *
 * @author weaversAndroid
 */
public interface TemtproductlinklistDao extends BaseDao<Temtproductlinklist> {

    List<Temtproductlinklist> getAllTempProduct(int start, int limit);
    
    List<Temtproductlinklist> getUnprocessedTempProduct();
    
    Temtproductlinklist getTemtproductlinklistByLink(String link);
    
    List<TempProduct> test();
}
