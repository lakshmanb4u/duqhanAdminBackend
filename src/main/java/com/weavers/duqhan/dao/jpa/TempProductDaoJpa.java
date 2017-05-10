/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.TempProductDao;
import com.weavers.duqhan.domain.TempProduct;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author weaversAndroid
 */
public class TempProductDaoJpa extends BaseDaoJpa<TempProduct> implements TempProductDao {

    public TempProductDaoJpa() {
        super(TempProduct.class, "TempProduct");
    }

    @Override
    public List<TempProduct> getAllAvailableProduct(int start, int limit) {
        Query query = getEntityManager().createQuery("SELECT p FROM TempProduct AS p ORDER BY p.lastUpdate DESC").setFirstResult(start).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public TempProduct getProductByExternelLink(String externalLink) {
        try {
            Query query = getEntityManager().createQuery("SELECT p FROM TempProduct AS p WHERE p.externalLink=:externalLink");
            query.setParameter("externalLink", externalLink);
            return (TempProduct) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
