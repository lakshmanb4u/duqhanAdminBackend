/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.business.impl;

import com.weavers.duqhan.business.AdminService;
import com.weavers.duqhan.business.MailService;
import com.weavers.duqhan.dao.CategoryDao;
import com.weavers.duqhan.dao.DuqhanAdminDao;
import com.weavers.duqhan.dao.OrderDetailsDao;
import com.weavers.duqhan.dao.OrderWorkflowDao;
import com.weavers.duqhan.dao.ProductDao;
import com.weavers.duqhan.dao.ProductPropertiesDao;
import com.weavers.duqhan.dao.ProductPropertyvaluesDao;
import com.weavers.duqhan.dao.UserAddressDao;
import com.weavers.duqhan.dao.VendorDao;
import com.weavers.duqhan.domain.Category;
import com.weavers.duqhan.domain.DuqhanAdmin;
import com.weavers.duqhan.domain.OrderDetails;
import com.weavers.duqhan.domain.OrderWorkflow;
import com.weavers.duqhan.domain.Product;
import com.weavers.duqhan.domain.ProductProperties;
import com.weavers.duqhan.domain.ProductPropertiesMap;
import com.weavers.duqhan.domain.ProductPropertyvalues;
import com.weavers.duqhan.domain.UserAddress;
import com.weavers.duqhan.domain.Users;
import com.weavers.duqhan.domain.Vendor;
import com.weavers.duqhan.dto.AddressDto;
import com.weavers.duqhan.dto.AouthBean;
import com.weavers.duqhan.dto.CategoryDto;
import com.weavers.duqhan.dto.LoginBean;
import com.weavers.duqhan.dto.OrderDto;
import com.weavers.duqhan.dto.OrderListDto;
import com.weavers.duqhan.dto.OrderWorkflowDto;
import com.weavers.duqhan.dto.StatusBean;
import com.weavers.duqhan.util.DateFormater;
import com.weavers.duqhan.util.GoogleBucketFileUploader;
import com.weavers.duqhan.util.RandomCodeGenerator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    @Autowired
    OrderDetailsDao orderDetailsDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    ProductPropertyvaluesDao productPropertyvaluesDao;
    @Autowired
    ProductPropertiesDao productPropertiesDao;
    @Autowired
    UserAddressDao userAddressDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    MailService mailService;
    @Autowired
    OrderWorkflowDao orderWorkflowDao;

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
    @Override
    public void getCategoryList(CategoryDto categoryDto) {
    	List<Category> categories = new ArrayList<Category>();
    	categories=categoryDao.getCategory(categoryDto.getStart(), categoryDto.getLimit());
    	categoryDto.setCategories(categories);
    	categoryDto.setStatusCode("200");
    }
    @Override
    public void getOrderList(OrderListDto orderListDto) {
        List<OrderDto> orderDtos = new ArrayList<>();
        List<Object[]> allObjects = new ArrayList<Object[]>();
        if(Objects.nonNull(orderListDto.getOrderStatus()) && !(orderListDto.getOrderStatus().isEmpty())){
        	 allObjects = orderDetailsDao.getOrderDetailsListByStatus(orderListDto.getStart(), orderListDto.getLimit(),orderListDto.getOrderStatus());
        }else{
             allObjects = orderDetailsDao.getOrderDetailsList(orderListDto.getStart(), orderListDto.getLimit());
        }
        for (Object[] objectArray : allObjects) {
            OrderDetails orderDetails = (OrderDetails) objectArray[0];
            ProductPropertiesMap propertyMap = (ProductPropertiesMap) objectArray[1];
            Users user = (Users) objectArray[2];
            UserAddress userAddress = userAddressDao.loadById(orderDetails.getAddressId());
            AddressDto addressDto = new AddressDto();
            addressDto.setAddressId(userAddress.getId());
            addressDto.setCity(userAddress.getCity());
            addressDto.setCompanyName(userAddress.getCompanyName());
            addressDto.setContactName(userAddress.getContactName());
            addressDto.setCountry(userAddress.getCountry());
            addressDto.setPhone(userAddress.getPhone());
            addressDto.setStreetOne(userAddress.getStreetOne());
            addressDto.setStreetTwo(userAddress.getStreetTwo());
            addressDto.setZipCode(userAddress.getZipCode());
            addressDto.setState(userAddress.getState());
            Product product = productDao.loadById(propertyMap.getProductId().getId());
            OrderDto orderDto = new OrderDto();
            orderDto.setId(orderDetails.getId());
            orderDto.setDate(DateFormater.formate(orderDetails.getOrderDate()));
            orderDto.setProductId(product.getId());
            orderDto.setProductName(product.getName());
            orderDto.setExternalLink(product.getExternalLink());
            orderDto.setQuantity(orderDetails.getQuentity());
            orderDto.setUserName(user.getName());
            orderDto.setEmail(user.getEmail());
            orderDto.setPrice(orderDetails.getPaymentAmount());
            orderDto.setOrderId(orderDetails.getOrderId());
            orderDto.setOrderStatus(orderDetails.getStatus());
            orderDto.setImgurl(product.getImgurl());
            orderDto.setAddress(addressDto);
            
            String[] propertyValueIds = new String[0];
            propertyValueIds = propertyMap.getPropertyvalueComposition().split("_");
            HashMap<String, String> propertiesMap = new HashMap<>();
            if (propertyValueIds.length > 0) {
                for (String propertyValueId : propertyValueIds) {
                    try {
                        ProductPropertyvalues productPropertyvalues = productPropertyvaluesDao.loadById(Long.valueOf(propertyValueId));
                        ProductProperties properties = productPropertiesDao.loadById(productPropertyvalues.getPropertyId());
                        propertiesMap.put(properties.getPropertyName(), productPropertyvalues.getValueName());
                    } catch (Exception nfe) {
                    }
                }
            }
            orderDto.setProperties(propertiesMap);
            orderDtos.add(orderDto);
            orderListDto.setOrderDtos(orderDtos);
            orderListDto.setStatusCode("200");
        }
    }

    @Override
    public void changeOrderStatus(StatusBean statusBean) {
        statusBean.setStatusCode("500");
        OrderDetails orderDetails = orderDetailsDao.loadById(statusBean.getId());
        if (orderDetails != null && statusBean.getStatus() != null) {
            orderDetails.setStatus(statusBean.getStatus());
            OrderDetails savedOrderDetails = orderDetailsDao.save(orderDetails);
            if (savedOrderDetails != null) {
                mailService.mailToUserForOrderStatusChange(savedOrderDetails);
                statusBean.setStatusCode("200");
            }
        }
    }

	@Override
	public void getOrderWorkflowList(OrderWorkflowDto orderWorkFlowDto) {
		//orderWorkFlowDto.setOrderWorkflow(orderWorkflowDao.getAllOrderWorkflow());
		List<OrderWorkflow> orderWorkflowData = orderWorkflowDao.getAllOrderWorkflow();
		Map<String,List<OrderWorkflow>> orderworkflow = new HashMap<>();
		for(OrderWorkflow owf : orderWorkflowData){
			String key = owf.getModule();
			List<OrderWorkflow> data = orderworkflow.get(key);
			if(data == null) {
				data = new ArrayList<OrderWorkflow>();
				orderworkflow.put(key, data);
			}
			data.add(owf);
		}
		orderWorkFlowDto.setOrderWorkflowList(orderworkflow);
		
	}

	@Override
	public void changeImage(String categoryId, MultipartFile file) {
		Long catId = new Long(categoryId);
		String menuIcon=GoogleBucketFileUploader.uploadCategoryImage(file, catId);
		categoryDao.updateCategoryImage(catId, menuIcon);
	}



}
