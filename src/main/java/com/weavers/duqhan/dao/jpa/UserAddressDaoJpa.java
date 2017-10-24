/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dao.jpa;

import com.weavers.duqhan.dao.UserAddressDao;
import com.weavers.duqhan.domain.UserAddress;

/**
 *
 * @author weaversAndroid
 */
public class UserAddressDaoJpa extends BaseDaoJpa<UserAddress> implements UserAddressDao {

    public UserAddressDaoJpa() {
        super(UserAddress.class, "UserAddress");
    }

}
