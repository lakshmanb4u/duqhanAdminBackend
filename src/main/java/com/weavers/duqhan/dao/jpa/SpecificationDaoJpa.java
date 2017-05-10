/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.SpecificationDao;
import com.weavers.duqhan.domain.Specification;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author weaversAndroid
 */
public class SpecificationDaoJpa extends BaseDaoJpa<Specification> implements SpecificationDao {

    public SpecificationDaoJpa() {
        super(Specification.class, "Specification");
    }

    @Override
    public List<Specification> getSpecificationsByCategoryId(Long categoryId) {
        Query query = getEntityManager().createQuery("SELECT sp FROM Specification AS sp WHERE sp.categoryId=:categoryId");
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

}
