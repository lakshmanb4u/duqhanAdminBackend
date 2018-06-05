/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao;

import com.weavers.duqhan.domain.DuqhanAdmin;

/**
 *
 * @author weaversAndroid
 */
public interface DuqhanAdminDao extends BaseDao<DuqhanAdmin> {

    DuqhanAdmin getAdminByEmailAndPassword(String email, String password);
    
    DuqhanAdmin getTokenByMail(String mail);

    DuqhanAdmin getTokenByMailAndUserId(String mail, Long userId);

    DuqhanAdmin getTokenByEmailAndToken(String email, String token);

    DuqhanAdmin getUserIdByTokenIfValid(String token);
}
