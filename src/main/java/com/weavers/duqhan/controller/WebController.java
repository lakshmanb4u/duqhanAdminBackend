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
import java.util.Collections;
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

    @RequestMapping(value = "/scraping-links", method = RequestMethod.GET) // scraping product from list
    @ResponseBody
    public String scrapingLinks(@RequestParam String link) {
        productService.getTempProductLinks(link);
        return "wait'n watch..";
    }

    @RequestMapping(value = "/scraping", method = RequestMethod.GET) // scraping product from list
    @ResponseBody
    public String scraping(@RequestParam int start, @RequestParam int limit) {
        List<StatusBean> statusBeans = new ArrayList<>();
        for (int i = start; i < start + limit; i++) {
            StatusBean bean = new StatusBean();
            bean.setId(Long.valueOf(i));
            statusBeans.add(bean);
        }
        Collections.shuffle(statusBeans);
        productService.loadTempProducts(statusBeans);
        return "wait'n watch..";
    }

}
