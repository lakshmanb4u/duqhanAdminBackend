/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.business;

import com.weavers.duqhan.domain.Category;
import com.weavers.duqhan.domain.DuqhanAdmin;
import com.weavers.duqhan.dto.AddressDto;
import com.weavers.duqhan.dto.AouthBean;
import com.weavers.duqhan.dto.CategoryDto;
import com.weavers.duqhan.dto.LoginBean;
import com.weavers.duqhan.dto.OrderListDto;
import com.weavers.duqhan.dto.OrderWorkflowDto;
import com.weavers.duqhan.dto.StatusBean;

import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

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

    String uploadProductImage(Long productId, MultipartFile file);

    void deleteProductImage(String oldImgUrl);
    
    void getOrderList(OrderListDto orderListDto);
    
    void getCategoryList(CategoryDto categoryDto);
    
    void changeOrderStatus(StatusBean statusBean);
    
    void getOrderWorkflowList(OrderWorkflowDto orderWorkFlowDto);

	void changeImage(String categoryId, MultipartFile file);
    
}
