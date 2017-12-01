/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.CategoryDao;
import com.weavers.duqhan.domain.Category;
import com.weavers.duqhan.dto.CategoryDto;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Android-3
 */
public class CategoryDaoJpa extends BaseDaoJpa<Category> implements CategoryDao {

    public CategoryDaoJpa() {
        super(Category.class, "Category");
    }

    @Override
    public List<Category> getChildByParentId(Long parentId) {
        Query query = getEntityManager().createQuery("SELECT c FROM Category c WHERE c.parentId=:parentId");
        query.setParameter("parentId", parentId);
        return query.getResultList();
    }

    @Override
    public Category getCategoryByName(String name) {
        try {
            Query query = getEntityManager().createQuery("SELECT c FROM Category c WHERE c.name=:name");
            query.setParameter("name", name);
            return (Category) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
