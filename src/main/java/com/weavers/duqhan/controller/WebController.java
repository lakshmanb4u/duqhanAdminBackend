/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.controller;

import com.weavers.duqhan.business.AdminService;
import com.weavers.duqhan.business.ProductService;
import com.weavers.duqhan.dto.AouthBean;
import com.weavers.duqhan.dto.LoginBean;
import com.weavers.duqhan.dto.StatusBean;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Weavers-web
 */
@CrossOrigin
@Controller
public class WebController {

    @Autowired
    AdminService adminService;
    @Autowired
    ProductService productService;

    @RequestMapping(value = "/", method = RequestMethod.GET) // open home page
    @ResponseBody
    public String home() {
        return "Hi how are you?..I am fine.";
    }

    @RequestMapping(value = "/admin-login", method = RequestMethod.POST)  // Log in by email only register user. Auth-Token generate.
    @ResponseBody
    public AouthBean trainerLogin(HttpServletResponse response, @RequestBody LoginBean loginBean) {
        AouthBean userBean = adminService.generatAccessToken(loginBean);
        return userBean;
    }

    @RequestMapping(value = "/testom", method = RequestMethod.GET) // open home page
    @ResponseBody
    public List<StatusBean> testom(@RequestParam int start,@RequestParam int limit) {
        List<StatusBean> statusBeans = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            StatusBean bean = new StatusBean();
            bean.setId((start + (long) (Math.random() * limit)));
            statusBeans.add(bean);
        }
        return productService.loadTempProducts(statusBeans);
    }

}
