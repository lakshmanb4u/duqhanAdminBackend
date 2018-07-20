/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.ProductDao;
import com.weavers.duqhan.domain.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author Android-3
 */
public class ProductDaoJpa extends BaseDaoJpa<Product> implements ProductDao {

    public ProductDaoJpa() {
        super(Product.class, "Product");
    }

    @Override
    public List<Product> loadByIds(List<Long> productIds) {
        if (productIds.isEmpty()) {
            return new ArrayList<>();
        }
        String q = "SELECT p FROM Product AS p WHERE p.id IN (";
        int i = 0;
        String s = "";
        for (Long productId : productIds) {
            s = s + (i == 0 ? "" : ",") + ":id" + i++;
        }
        Query query = getEntityManager().createQuery(q + s + ")");
        i = 0;
        for (Long productId : productIds) {
            query.setParameter("id" + i++, productId);
        }
        return query.getResultList();
    }

    @Override
    public List<Product> getAllAvailableProductByProductIds(List<Long> productIds) {    //get All Available Product from ProductIds.
        if (productIds.isEmpty()) {
            return new ArrayList<>();
        }
        String q = "SELECT p FROM Product AS p WHERE p.id IN (";
        int i = 0;
        String s = null;
        for (Long productId : productIds) {
            s = s + (i == 0 ? "" : ",") + "id" + i++;
        }
        Query query = getEntityManager().createQuery(q + s + ")");
        i = 0;
        for (Long productId : productIds) {
            query.setParameter("id" + i++, productId);
        }
        return query.getResultList();
    }

    @Override
    public List<Product> getAllRecentViewProduct(Long userid, int start, int limit) {
        Query query = getEntityManager().createQuery("SELECT p FROM Product AS p WHERE p.id IN (SELECT rv.productId FROM RecentView AS rv WHERE rv.userId=:userId ORDER BY rv.viewDate DESC)").setFirstResult(start).setMaxResults(limit);
        query.setParameter("userId", userid);
        return query.getResultList();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        Query query = getEntityManager().createQuery("SELECT p FROM Product AS p WHERE p.categoryId=:categoryId ORDER BY p.lastUpdate DESC");
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    @Override
    public List<Product> getProductsByCategoryIncludeChild(Long categoryId, int start, int limit) {
        Query query = getEntityManager().createQuery("SELECT p FROM Product AS p WHERE p.categoryId=:categoryId OR p.parentPath LIKE :parentPath ORDER BY p.lastUpdate DESC").setFirstResult(start).setMaxResults(limit);
        query.setParameter("categoryId", categoryId);
        query.setParameter("parentPath", "%=" + categoryId + "=%");
        return query.getResultList();
    }

    @Override
    public List<Product> getAllAvailableProduct(int start, int limit) {
        Query query = getEntityManager().createQuery("SELECT p FROM Product AS p ORDER BY p.lastUpdate DESC").setFirstResult(start).setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<Product> SearchProductByNameAndDescription(String searchName, int start, int limit) {
    	SessionFactory sessionFactory = getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(Product.class).get();
       
        
        
           org.apache.lucene.search.Query query =qb.keyword().fuzzy().withPrefixLength(0).onFields("name","description").matching(searchName).createQuery();
            	
            	
        
           org.hibernate.Query hibQuery = 
                fullTextSession.createFullTextQuery(query, Product.class).setFirstResult(start).setMaxResults(limit);
        	
           List<Product> result = hibQuery.list();
           
           return result;
    }

    @Override
    public Product getProductByExternelLink(String externalLink) {
        try {
            Query query = getEntityManager().createQuery("SELECT p FROM Product AS p WHERE p.externalLink=:externalLink");
            query.setParameter("externalLink", externalLink);
            return (Product) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }catch (NonUniqueResultException nre) {
            return new Product();
        }
    }
}
