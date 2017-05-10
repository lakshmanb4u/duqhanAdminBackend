/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.ColorDao;
import com.weavers.duqhan.domain.Color;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Android-3
 */
public class ColorDaoJpa extends BaseDaoJpa<Color> implements ColorDao {

    public ColorDaoJpa() {
        super(Color.class, "Color");
    }

    @Override
    public List<Color> loadByIds(List<Long> colorIds) { // load colors by color ids
        if (colorIds.isEmpty()) {
            return new ArrayList<>();
        }
        String q = "SELECT c FROM Color AS c WHERE c.id IN (";
        int i = 0;
        String s = "";
        for (Long colorId : colorIds) {
            s = s + (i == 0 ? "" : ",") + ":id" + i++;
        }
        Query query = getEntityManager().createQuery(q + s + ")");
        i = 0;
        for (Long colorId : colorIds) {
            query.setParameter("id" + i++, colorId);
        }
        return query.getResultList();
    }

}
