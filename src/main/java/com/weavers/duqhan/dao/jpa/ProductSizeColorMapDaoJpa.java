/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.ProductSizeColorMapDao;
import com.weavers.duqhan.domain.ProductSizeColorMap;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Android-3
 */
public class ProductSizeColorMapDaoJpa extends BaseDaoJpa<ProductSizeColorMap> implements ProductSizeColorMapDao {

    public ProductSizeColorMapDaoJpa() {
        super(ProductSizeColorMap.class, "ProductSizeColorMap");
    }

    @Override
    public List<ProductSizeColorMap> loadByIds(List<Long> ids) {
        String q = "SELECT map FROM ProductSizeColorMap AS map where map.id in (";
        int i = 0;
        String s = "";
        for (Long id : ids) {
            s = s + (i == 0 ? "" : ",") + ":id" + i++;
        }
        Query query = getEntityManager().createQuery(q + s + ")");
        i = 0;
        for (Long id : ids) {
            query.setParameter("id" + i++, id);
        }
        return query.getResultList();
    }
    
    @Override
    public HashMap<Long, ProductSizeColorMap> getSizeColorMapbyMinPriceIfAvailable(List<Long> productIds) {
        HashMap<Long, ProductSizeColorMap> mapSizeColorMap = new HashMap<>();
        if (!productIds.isEmpty()) {
            String q = "SELECT pmap.productId, MIN(pmap.discount), price, SUM(pmap.quentity) FROM ProductSizeColorMap AS pmap WHERE pmap.quentity>0 AND pmap.productId IN (";
            int i = 0;
            String s = "";
            for (Long productId : productIds) {
                s = s + (i == 0 ? "" : ",") + ":productId" + i++;
            }
            Query query = getEntityManager().createQuery(q + s + ") GROUP BY pmap.productId, pmap.price");
            i = 0;
            for (Long productId : productIds) {
                query.setParameter("productId" + i++, productId);
            }
            List<Object[]> objects = query.getResultList();
            if (!objects.isEmpty()) {
                Long c = 0l;
                for (Object[] object : objects) {
                    if (mapSizeColorMap.containsKey((Long) object[0])) {    // if map contains then update
                        if (mapSizeColorMap.get((Long) object[0]).getDiscount() > (Double) object[1]) {
                            mapSizeColorMap.get((Long) object[0]).setDiscount((Double) object[1]);
                            mapSizeColorMap.get((Long) object[0]).setPrice((Double) object[2]);
                            mapSizeColorMap.get((Long) object[0]).setQuentity(mapSizeColorMap.get((Long) object[0]).getQuentity() + (long) object[3]);
                        }
                    } else {    // else add new
                        ProductSizeColorMap productSizeColorMap = new ProductSizeColorMap();
                        productSizeColorMap.setProductId((Long) object[0]);
                        productSizeColorMap.setDiscount((Double) object[1]);
                        productSizeColorMap.setPrice((Double) object[2]);
                        productSizeColorMap.setQuentity((long) object[3]);
                        mapSizeColorMap.put((Long) object[0], productSizeColorMap);
                    }

                }
            }
        }
//        Query query = getEntityManager().createQuery("SELECT pmap.productId, MIN(pmap.discount), price FROM ProductSizeColorMap AS pmap WHERE pmap.quentity>0 AND pmap.productId IN (1) GROUP BY pmap.productId, pmap.price");
//        query.getResultList();
        return mapSizeColorMap;
    }

    @Override
    public HashMap<Long, ProductSizeColorMap> getSizeColorMapbyMinPriceRecentView(List<Long> productIds) {
        HashMap<Long, ProductSizeColorMap> mapSizeColorMap = new HashMap<>();
        if (!productIds.isEmpty()) {
            String q = "SELECT pmap.productId, MIN(pmap.discount), price, SUM(pmap.quentity) FROM ProductSizeColorMap AS pmap WHERE pmap.productId IN (";
            int i = 0;
            String s = "";
            for (Long productId : productIds) {
                s = s + (i == 0 ? "" : ",") + ":productId" + i++;
            }
            Query query = getEntityManager().createQuery(q + s + ") GROUP BY pmap.productId, pmap.price");
            i = 0;
            for (Long productId : productIds) {
                query.setParameter("productId" + i++, productId);
            }
            List<Object[]> objects = query.getResultList();
            if (!objects.isEmpty()) {
                Long c = 0l;
                for (Object[] object : objects) {
                    if (mapSizeColorMap.containsKey((Long) object[0])) {    // if map contains then update
                        if (mapSizeColorMap.get((Long) object[0]).getDiscount() > (Double) object[1]) {
                            mapSizeColorMap.get((Long) object[0]).setDiscount((Double) object[1]);
                            mapSizeColorMap.get((Long) object[0]).setPrice((Double) object[2]);
                            mapSizeColorMap.get((Long) object[0]).setQuentity(mapSizeColorMap.get((Long) object[0]).getQuentity() + (long) object[3]);
                        }
                    } else {    // else add new
                        ProductSizeColorMap productSizeColorMap = new ProductSizeColorMap();
                        productSizeColorMap.setProductId((Long) object[0]);
                        productSizeColorMap.setDiscount((Double) object[1]);
                        productSizeColorMap.setPrice((Double) object[2]);
                        productSizeColorMap.setQuentity((long) object[3]);
                        mapSizeColorMap.put((Long) object[0], productSizeColorMap);
                    }

                }
            }
        }
//        Query query = getEntityManager().createQuery("SELECT pmap.productId, MIN(pmap.discount), price FROM ProductSizeColorMap AS pmap WHERE pmap.quentity>0 AND pmap.productId IN (1) GROUP BY pmap.productId, pmap.price");
//        query.getResultList();
        return mapSizeColorMap;
    }

    @Override
    public List<ProductSizeColorMap> getSizeColorMapByProductId(Long productId) {
        Query query = getEntityManager().createQuery("SELECT map FROM ProductSizeColorMap AS map WHERE map.productId=:productId");
        query.setParameter("productId", productId);
        return query.getResultList();
    }

    @Override
    public HashMap<Long, ProductSizeColorMap> getSizeColorMapByProductIds(List<Long> productIds) {
        HashMap<Long, ProductSizeColorMap> mapSizeColorMap = new HashMap<>();
        if (!productIds.isEmpty()) {
            String q = "SELECT pmap.productId, AVG(pmap.discount), AVG(pmap.price), SUM(pmap.quentity) FROM ProductSizeColorMap AS pmap WHERE pmap.productId IN (";
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
                    ProductSizeColorMap productSizeColorMap = new ProductSizeColorMap();
                    productSizeColorMap.setProductId((Long) object[0]);
                    productSizeColorMap.setDiscount((Double) object[1]);
                    productSizeColorMap.setPrice((Double) object[2]);
                    productSizeColorMap.setQuentity((long) object[3]);
                    mapSizeColorMap.put((Long) object[0], productSizeColorMap);
                }
            }
        }
        return mapSizeColorMap;
    }
}
