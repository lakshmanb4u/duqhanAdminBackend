/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao;

import com.weavers.duqhan.domain.OrderDetails;
import java.util.List;

/**
 *
 * @author weaversAndroid
 */
public interface OrderDetailsDao extends BaseDao<OrderDetails> {

    List<Object[]> getOrderDetailsList(int start, int limit);

	List<Object[]> getOrderDetailsListByStatus(int start, int limit, String orderStatus);
}
