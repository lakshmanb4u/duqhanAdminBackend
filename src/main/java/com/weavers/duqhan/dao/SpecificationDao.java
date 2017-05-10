/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao;

import com.weavers.duqhan.domain.Specification;
import java.util.List;

/**
 *
 * @author weaversAndroid
 */
public interface SpecificationDao extends BaseDao<Specification> {
    
    List<Specification> getSpecificationsByCategoryId(Long categoryId);
}
