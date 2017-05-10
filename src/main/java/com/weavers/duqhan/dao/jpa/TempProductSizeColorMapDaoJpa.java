/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.TempProductSizeColorMapDao;
import com.weavers.duqhan.domain.ProductSizeColorMap;
import com.weavers.duqhan.domain.TempProductSizeColorMap;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author weaversAndroid
 */
public class TempProductSizeColorMapDaoJpa extends BaseDaoJpa<TempProductSizeColorMap> implements TempProductSizeColorMapDao {

    public TempProductSizeColorMapDaoJpa() {
        super(TempProductSizeColorMap.class, "TempProductSizeColorMap");
    }

    @Override
    public HashMap<Long, TempProductSizeColorMap> getSizeColorMapByProductIds(List<Long> productIds) {
        HashMap<Long, TempProductSizeColorMap> mapSizeColorMap = new HashMap<>();
        if (!productIds.isEmpty()) {
            String q = "SELECT pmap.productId, AVG(pmap.discount), AVG(pmap.price), SUM(pmap.quantity) FROM TempProductSizeColorMap AS pmap WHERE pmap.productId IN (";
            int i = 0;
            String s = "";
            for (Long productId : productIds) {
                s = s + (i == 0 ? "" : ",") + ":productId" + i++;
            }
            Query query = getEntityManager().createQuery(q + s + ") GROUP BY pmap.productId");
            i = 0;
            for (Long productId : productIds) {
                query.setParameter("productId" + i++, productId);
            }
            List<Object[]> objects = query.getResultList();
            if (!objects.isEmpty()) {
                Long c = 0l;
                for (Object[] object : objects) {
                    TempProductSizeColorMap productSizeColorMap = new TempProductSizeColorMap();
                    productSizeColorMap.setProductId((Long) object[0]);
                    productSizeColorMap.setDiscount((Double) object[1]);
                    productSizeColorMap.setPrice((Double) object[2]);
                    productSizeColorMap.setQuantity((long) object[3]);
                    mapSizeColorMap.put((Long) object[0], productSizeColorMap);
                }
            }
        }
        return mapSizeColorMap;
    }

    @Override
    public List<TempProductSizeColorMap> getSizeColorMapByProductId(Long productId) {
        Query query = getEntityManager().createQuery("SELECT map FROM TempProductSizeColorMap AS map WHERE map.productId=:productId");
        query.setParameter("productId", productId);
        return query.getResultList();
    }
}
