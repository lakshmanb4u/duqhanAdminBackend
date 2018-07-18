/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.controller;

import com.weavers.duqhan.business.AdminService;
import com.weavers.duqhan.business.ProductService;
import com.weavers.duqhan.dao.TemtproductlinklistDao;
import com.weavers.duqhan.domain.Category;
import com.weavers.duqhan.domain.DuqhanAdmin;
import com.weavers.duqhan.dto.CategoryDto;
import com.weavers.duqhan.dto.LoginBean;
import com.weavers.duqhan.dto.OrderListDto;
import com.weavers.duqhan.dto.OrderWorkflowDto;
import com.weavers.duqhan.dto.StatusBean;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author weaversAndroid
 */
@CrossOrigin
@Controller
@RequestMapping("/admin/**")
public class AdminController {

    @Autowired
    ProductService productService;
    @Autowired
    AdminService adminService;
    @Autowired
    TemtproductlinklistDao temtproductlinklistDao;

    @RequestMapping(value = "/logout", method = RequestMethod.POST) //logout, destroy auth token.
    @ResponseBody
    public StatusBean logOut(HttpServletRequest request, HttpServletResponse response1, @RequestBody LoginBean loginBean) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean statusBean = new StatusBean();
        if (admin != null) {
            loginBean.setAuthtoken(request.getHeader("X-Auth-Token"));  // Check whether Auth-Token is valid, provided by user
            statusBean.setStatus(adminService.invalidatedToken(loginBean.getEmail(), loginBean.getAuthtoken()));
        } else {
            response1.setStatus(401);
            statusBean.setStatusCode("401");
            statusBean.setStatus("Invalid Token.");
        }
        return statusBean;
    }

    @RequestMapping(value = "/get-orderlist", method = RequestMethod.POST) //logout, destroy auth token.
    @ResponseBody
    public OrderListDto getOrderList(HttpServletRequest request, HttpServletResponse response1, @RequestBody OrderListDto orderListDto) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        if (admin != null) {
            adminService.getOrderList(orderListDto);
        } else {
            response1.setStatus(401);
            orderListDto.setStatusCode("401");
            orderListDto.setStatus("Invalid Token.");
        }
        return orderListDto;
    }
    
    @RequestMapping(value = "/get-categories", method = RequestMethod.POST) //logout, destroy auth token.
    @ResponseBody
    public CategoryDto getCategories(HttpServletRequest request, HttpServletResponse response1, @RequestBody CategoryDto categoryDto) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        if (admin != null) {
            adminService.getCategoryList(categoryDto);
        } else {
            response1.setStatus(401);
            categoryDto.setStatusCode("401");
            categoryDto.setStatus("Invalid Token.");
        }
        return categoryDto;
    }
    
    @RequestMapping(value = "/get-all-categories", method = RequestMethod.POST) //logout, destroy auth token.
    @ResponseBody
    public CategoryDto getAllCategories(HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        CategoryDto categoryDto = new CategoryDto();
        if (admin != null) {
        	categoryDto=adminService.getAllCategoryList();
        } else {
            response1.setStatus(401);
            categoryDto.setStatusCode("401");
            categoryDto.setStatus("Invalid Token.");
        }
        return categoryDto;
    }

    @RequestMapping(value = "/changeOrderStatus", method = RequestMethod.POST) //logout, destroy auth token.
    @ResponseBody
    public StatusBean changeOrderStatus(HttpServletRequest request, HttpServletResponse response1, @RequestBody StatusBean statusBean) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        if (admin != null) {
            adminService.changeOrderStatus(statusBean);
        } else {
            response1.setStatus(401);
            statusBean.setStatusCode("401");
            statusBean.setStatus("Invalid Token.");
        }
        return statusBean;
    }
    
    @RequestMapping(value = "/orderWorkflow", method = RequestMethod.GET)
    @ResponseBody
    public OrderWorkflowDto getAllOrderWorkflow(HttpServletRequest request, HttpServletResponse response1) {
    	DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));
    	OrderWorkflowDto orderWorkFlowDto = new OrderWorkflowDto();
    	if(admin != null) {
    		adminService.getOrderWorkflowList(orderWorkFlowDto);
    	} else {
    		response1.setStatus(401);
    		orderWorkFlowDto.setStatus("Invalid Token.");
    		orderWorkFlowDto.setStatusCode("401");
    	}
    	
    	return orderWorkFlowDto;
    }
    
    @RequestMapping(value = "/order/request_return", method = RequestMethod.POST)   //return order
    public CategoryDto saveCateoryImage(HttpServletResponse response, HttpServletRequest request, @RequestParam("categoryId") String categoryId,@RequestParam("file") MultipartFile file) {
    	DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));
    	CategoryDto categoryDto = new CategoryDto();
    	if(admin != null) {
    		adminService.changeImage(categoryId,file);
    	} else {
    		response.setStatus(401);
    		categoryDto.setStatus("Invalid Token.");
    		categoryDto.setStatusCode("401");
    	}
    	return null;
    }
}