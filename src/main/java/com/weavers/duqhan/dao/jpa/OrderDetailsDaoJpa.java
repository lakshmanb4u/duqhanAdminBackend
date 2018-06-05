/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.OrderDetailsDao;
import com.weavers.duqhan.domain.OrderDetails;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author weaversAndroid
 */
public class OrderDetailsDaoJpa extends BaseDaoJpa<OrderDetails> implements OrderDetailsDao {

    public OrderDetailsDaoJpa() {
        super(OrderDetails.class, "OrderDetails");
    }

    @Override
    public List<Object[]> getOrderDetailsList(int start, int limit) {
        Query query = getEntityManager().createQuery("SELECT od, map, u FROM OrderDetails AS od, ProductPropertiesMap AS map,Users AS u WHERE od.userId=u.id AND od.mapId=map.id AND od.status!=:status ORDER BY od.orderDate DESC").setFirstResult(start).setMaxResults(limit);
        query.setParameter("status", "failed");
        return query.getResultList();
    }

}
