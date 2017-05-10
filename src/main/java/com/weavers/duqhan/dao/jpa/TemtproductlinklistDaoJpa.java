/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.TemtproductlinklistDao;
import com.weavers.duqhan.domain.Temtproductlinklist;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author weaversAndroid
 */
public class TemtproductlinklistDaoJpa extends BaseDaoJpa<Temtproductlinklist> implements TemtproductlinklistDao {

    public TemtproductlinklistDaoJpa() {
        super(Temtproductlinklist.class, "Temtproductlinklist");
    }

    @Override
    public List<Temtproductlinklist> getUnprocessedTempProduct() {
        Query query = getEntityManager().createQuery("SELECT tp FROM Temtproductlinklist AS tp WHERE tp.status =" + 0);
        return query.getResultList();
    }

    @Override
    public List<Temtproductlinklist> getAllTempProduct(int start, int limit) {
        Query query = getEntityManager().createQuery("SELECT tp FROM Temtproductlinklist AS tp WHERE tp.status IN(0, 1, 2)").setFirstResult(start).setMaxResults(limit);
        return query.getResultList();
    }

}
