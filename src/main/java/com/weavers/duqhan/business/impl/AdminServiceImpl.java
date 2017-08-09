/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.business.impl;

import com.weavers.duqhan.business.AdminService;
import com.weavers.duqhan.dao.DuqhanAdminDao;
import com.weavers.duqhan.dao.VendorDao;
import com.weavers.duqhan.domain.DuqhanAdmin;
import com.weavers.duqhan.domain.Vendor;
import com.weavers.duqhan.dto.AddressDto;
import com.weavers.duqhan.dto.AouthBean;
import com.weavers.duqhan.dto.LoginBean;
import com.weavers.duqhan.util.GoogleBucketFileUploader;
import com.weavers.duqhan.util.RandomCodeGenerator;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author weaversAndroid
 */
public class AdminServiceImpl implements AdminService {

    @Autowired
    DuqhanAdminDao duqhanAdminDao;
    @Autowired
    VendorDao vendorDao;

    @Override
    public String adminLogin(LoginBean loginBean, HttpSession session) {
        DuqhanAdmin duqhanAdmin = duqhanAdminDao.getAdminByEmailAndPassword(loginBean.getEmail(), loginBean.getPassword());
        if (duqhanAdmin != null) {
            session.setAttribute("admin", duqhanAdmin);
            return "admin/add-product";
        } else {
            return "web/adminlogin";
        }
    }

    private String createToken(Long userId) {
        String token = null;
        Calendar cal = Calendar.getInstance();
        if (userId != null) {
            token = RandomCodeGenerator.getNumericCode(4) + userId + cal.getTimeInMillis(); // generate new token
        }
        return token;
    }

    private Date nextUpdate() {
        Calendar calendar = Calendar.getInstance(); // set token validity
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    @Override
    public AouthBean generatAccessToken(LoginBean loginBean) {
        AouthBean aouthBean = new AouthBean();
        DuqhanAdmin duqhanAdmin = duqhanAdminDao.getAdminByEmailAndPassword(loginBean.getEmail(), loginBean.getPassword());
        String token = "";
        String email = "";
        if (duqhanAdmin != null) {    // for existing user update token
            token = this.createToken(duqhanAdmin.getId());
            duqhanAdmin.setAouthToken(token);
            duqhanAdmin.setValidTill(nextUpdate());
            DuqhanAdmin duqhanAdmin2 = duqhanAdminDao.save(duqhanAdmin);
            email = duqhanAdmin2.getEmail();
        }
        aouthBean.setAouthToken(token);
        aouthBean.setStatus(email);
        return aouthBean;
    }

    @Override
    public String invalidatedToken(String email, String token) {
        String status = "failure";
        DuqhanAdmin duqhanAdmin = duqhanAdminDao.getTokenByEmailAndToken(email, token);
        if (duqhanAdmin != null) {
            duqhanAdmin.setAouthToken(null); // Token invalidated when user log out
            duqhanAdminDao.save(duqhanAdmin);
            status = "success";
        }
        return status;
    }

    @Override
    public DuqhanAdmin getUserByToken(String token) {
        DuqhanAdmin duqhanAdmin = duqhanAdminDao.getUserIdByTokenIfValid(token);  // Find user id by token.
        if (duqhanAdmin != null) {
            return duqhanAdmin;
        } else {
            return null;
        }
    }

    @Override
    public String saveVendor(AddressDto addressDto) {
        Vendor vendor = new Vendor();
        vendor.setId(null);
        vendor.setCity(addressDto.getCity());
        vendor.setCountry(addressDto.getCountry());
        vendor.setPhone(addressDto.getPhone());
        vendor.setState(addressDto.getState());
        vendor.setStreetOne(addressDto.getStreetOne());
        vendor.setStreetTwo(addressDto.getStreetTwo());
        vendor.setVendorName(addressDto.getContactName());
        vendor.setZip(addressDto.getZipCode());
        Vendor vendor2 = vendorDao.save(vendor);
        if (vendor2 == null) {
            return "ERROR: Vendor can not be saved!!";
        } else {
            return "Vendor saved..";
        }
    }

    @Override
    public String uploadProductImage(Long productId, MultipartFile file) {
        String imgUrl = GoogleBucketFileUploader.uploadProductImage(file, productId);
        return imgUrl;
    }

    @Override
    public void deleteProductImage(String oldImgUrl) {
        if (oldImgUrl != null && oldImgUrl.contains("duqhan-images/")) {
            String imgName = oldImgUrl.split("duqhan-images/")[1];
            GoogleBucketFileUploader.deleteProductImg(imgName);
        }
    }

}
