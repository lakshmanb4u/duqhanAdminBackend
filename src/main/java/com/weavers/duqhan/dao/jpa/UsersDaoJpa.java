/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.UsersDao;
import com.weavers.duqhan.domain.Users;

/**
 *
 * @author weaversAndroid
 */
public class UsersDaoJpa extends BaseDaoJpa<Users> implements UsersDao {

    public UsersDaoJpa() {
        super(Users.class, "Users");
    }

}
