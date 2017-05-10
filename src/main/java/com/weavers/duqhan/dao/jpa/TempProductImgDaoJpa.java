/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.TempProductImgDao;
import com.weavers.duqhan.domain.TempProductImg;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author weaversAndroid
 */
public class TempProductImgDaoJpa extends BaseDaoJpa<TempProductImg> implements TempProductImgDao {

    public TempProductImgDaoJpa() {
        super(TempProductImg.class, "TempProductImg");
    }

    @Override
    public List<TempProductImg> getProductImgsByProductId(Long productId) {
        Query query = getEntityManager().createQuery("SELECT i FROM TempProductImg AS i WHERE i.productId=:productId");
        query.setParameter("productId", productId);
        return query.getResultList();
    }
}
