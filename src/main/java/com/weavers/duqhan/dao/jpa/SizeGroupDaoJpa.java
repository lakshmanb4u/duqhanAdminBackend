/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.SizeGroupDao;
import com.weavers.duqhan.domain.SizeGroup;

/**
 *
 * @author Android-3
 */
public class SizeGroupDaoJpa extends BaseDaoJpa<SizeGroup> implements SizeGroupDao{
    
    public SizeGroupDaoJpa() {
        super(SizeGroup.class, "SizeGroup");
    }
    
}
