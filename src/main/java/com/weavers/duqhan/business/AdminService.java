/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.business;

import com.weavers.duqhan.domain.DuqhanAdmin;
import com.weavers.duqhan.dto.AddressDto;
import com.weavers.duqhan.dto.AouthBean;
import com.weavers.duqhan.dto.LoginBean;
import javax.servlet.http.HttpSession;

/**
 *
 * @author weaversAndroid
 */
public interface AdminService {

    String adminLogin(LoginBean loginBean, HttpSession session);

    AouthBean generatAccessToken(LoginBean loginBean);

    String invalidatedToken(String email, String token);

    DuqhanAdmin getUserByToken(String token);
    
    public String saveVendor(AddressDto addressDto);
}
