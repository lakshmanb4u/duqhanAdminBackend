/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.SizeeDao;
import com.weavers.duqhan.domain.Sizee;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Android-3
 */
public class SizeeDaoJpa extends BaseDaoJpa<Sizee> implements SizeeDao {

    public SizeeDaoJpa() {
        super(Sizee.class, "Sizee");
    }

    @Override
    public List<Sizee> loadByIds(List<Long> sizeeIds) { // load size by ids
        if (sizeeIds.isEmpty()) {
            return new ArrayList<>();
        }
        String q = "SELECT s FROM Sizee AS s WHERE s.id IN (";
        int i = 0;
        String s = "";
        for (Long sizeeId : sizeeIds) {
            s = s + (i == 0 ? "" : ",") + ":id" + i++;
        }
        Query query = getEntityManager().createQuery(q + s + ")");
        i = 0;
        for (Long sizeeId : sizeeIds) {
            query.setParameter("id" + i++, sizeeId);
        }
        return query.getResultList();
    }

}
