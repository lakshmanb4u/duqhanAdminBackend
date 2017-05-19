/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.controller;

import com.weavers.duqhan.business.AdminService;
import com.weavers.duqhan.business.ProductService;
import com.weavers.duqhan.domain.DuqhanAdmin;
import com.weavers.duqhan.dto.AddressDto;
import com.weavers.duqhan.dto.CategoryDto;
import com.weavers.duqhan.dto.ColorAndSizeDto;
import com.weavers.duqhan.dto.LoginBean;
import com.weavers.duqhan.dto.ProductBean;
import com.weavers.duqhan.dto.ProductBeans;
import com.weavers.duqhan.dto.ProductDetailBean;
import com.weavers.duqhan.dto.ProductRequistBean;
import com.weavers.duqhan.dto.SizeDto;
import com.weavers.duqhan.dto.SpecificationDto;
import com.weavers.duqhan.dto.StatusBean;
import com.weavers.duqhan.dto.TransforDto;
import java.util.ArrayList;
import java.util.List;
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

    @RequestMapping(value = "/get-category", method = RequestMethod.GET) // have to remove when admin palen is ready.
    @ResponseBody
    public ColorAndSizeDto getCategory(HttpServletResponse response1, HttpServletRequest request) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        ColorAndSizeDto colorAndSizeDto;
        if (admin != null) {
            colorAndSizeDto = productService.getCategories();
        } else {
            response1.setStatus(401);
            colorAndSizeDto = new ColorAndSizeDto();
            colorAndSizeDto.setStatusCode("401");
            colorAndSizeDto.setStatus("Invalid Token.");
        }
        return colorAndSizeDto;
    }

    @RequestMapping(value = "/get-size", method = RequestMethod.GET) // have to remove when admin palen is ready.
    @ResponseBody
    public ColorAndSizeDto getSize(HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        ColorAndSizeDto colorAndSizeDto;
        if (admin != null) {
            colorAndSizeDto = productService.getSizes();
        } else {
            response1.setStatus(401);
            colorAndSizeDto = new ColorAndSizeDto();
            colorAndSizeDto.setStatusCode("401");
            colorAndSizeDto.setStatus("Invalid Token.");
        }
        return colorAndSizeDto;
    }

    @RequestMapping(value = "/get-sizegroup", method = RequestMethod.GET) // have to remove when admin palen is ready.
    @ResponseBody
    public ColorAndSizeDto getSizegroup(HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        ColorAndSizeDto colorAndSizeDto;
        if (admin != null) {

            colorAndSizeDto = productService.getSizeGroupe();
        } else {
            response1.setStatus(401);
            colorAndSizeDto = new ColorAndSizeDto();
            colorAndSizeDto.setStatusCode("401");
            colorAndSizeDto.setStatus("Invalid Token.");
        }
        return colorAndSizeDto;
    }

    @RequestMapping(value = "/get-color", method = RequestMethod.GET) // have to remove when admin palen is ready.
    @ResponseBody
    public ColorAndSizeDto getColor(HttpServletRequest request, HttpServletResponse response1) {

        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        ColorAndSizeDto colorAndSizeDto;
        if (admin != null) {
            colorAndSizeDto = productService.getColors();
        } else {
            response1.setStatus(401);
            colorAndSizeDto = new ColorAndSizeDto();
            colorAndSizeDto.setStatusCode("401");
            colorAndSizeDto.setStatus("Invalid Token.");
        }
        return colorAndSizeDto;
    }

    @RequestMapping(value = "/get-vendor", method = RequestMethod.GET) // have to remove when admin palen is ready.
    @ResponseBody
    public ColorAndSizeDto getVendor(HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        ColorAndSizeDto colorAndSizeDto;
        if (admin != null) {
            colorAndSizeDto = productService.getVendors();
        } else {
            response1.setStatus(401);
            colorAndSizeDto = new ColorAndSizeDto();
            colorAndSizeDto.setStatusCode("401");
            colorAndSizeDto.setStatus("Invalid Token.");
        }
        return colorAndSizeDto;
    }

    @RequestMapping(value = "/get-specifications", method = RequestMethod.GET) // have to remove when admin palen is ready.
    @ResponseBody
    public ColorAndSizeDto getSpecifications(@RequestParam Long categoryId, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        ColorAndSizeDto colorAndSizeDto;
        if (admin != null) {
            colorAndSizeDto = productService.getSpecificationsByCategoryId(categoryId);
        } else {
            response1.setStatus(401);
            colorAndSizeDto = new ColorAndSizeDto();
            colorAndSizeDto.setStatusCode("401");
            colorAndSizeDto.setStatus("Invalid Token.");
        }
        return colorAndSizeDto;
    }

    @RequestMapping(value = "/save-product", method = RequestMethod.POST)   // save a new product.
    @ResponseBody
    public StatusBean addingProduct(@RequestBody ProductBean productBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            response.setStatus(productService.saveProduct(productBean));
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/save-category", method = RequestMethod.POST)  // save a new category
    @ResponseBody
    public StatusBean saveCategory(@RequestBody CategoryDto categoryDto, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            response.setStatus(productService.saveCategory(categoryDto));
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/save-size", method = RequestMethod.POST)  // save new size
    @ResponseBody
    public StatusBean saveSize(@RequestBody SizeDto sizeDto, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            response.setStatus(productService.saveSize(sizeDto));
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/save-sizegroup", method = RequestMethod.POST) // save new sizegroup
    @ResponseBody
    public StatusBean saveSizeGroup(@RequestBody ProductRequistBean requistBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            response.setStatus(productService.saveSizeGroup(requistBean.getName()));
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/save-color", method = RequestMethod.POST) // save new color
    @ResponseBody
    public StatusBean saveColor(@RequestBody ProductRequistBean requistBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            response.setStatus(productService.saveColor(requistBean.getName()));
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/save-product-image", method = RequestMethod.POST)   // save product image.
    @ResponseBody
    public StatusBean addingProductImage(@RequestBody ProductBean productBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            response.setStatus("failure");
            response.setStatus(productService.saveProductImage(productBean));
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/save-vendor", method = RequestMethod.POST)  // save a new category
    @ResponseBody
    public StatusBean saveVendor(@RequestBody AddressDto addressDto, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            response.setStatus(adminService.saveVendor(addressDto));
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/save-specification", method = RequestMethod.POST)  // save a new Specification
    @ResponseBody
    public StatusBean saveSpecification(@RequestBody SpecificationDto specificationDto, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            response.setStatus(productService.saveSpecification(specificationDto));
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/save-specification-value", method = RequestMethod.POST)  // save a new Specification values
    @ResponseBody
    public StatusBean saveSpecificationValue(@RequestBody SpecificationDto specificationDto, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            response.setStatus(productService.saveSpecificationValue(specificationDto));
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/get-product-lise", method = RequestMethod.POST)  // 
    @ResponseBody
    public ProductBeans getProductList(@RequestBody ProductRequistBean requistBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        ProductBeans productBeans;
        if (admin != null) {
            productBeans = productService.getAllProductsIncloudeZeroAvailable(requistBean.getStart(), requistBean.getLimit());
        } else {
            response1.setStatus(401);
            productBeans = new ProductBeans();
            productBeans.setStatusCode("401");
            productBeans.setStatus("Invalid Token.");
        }
        return productBeans;
    }

    @RequestMapping(value = "/update-product-details", method = RequestMethod.POST)  // 
    @ResponseBody
    public StatusBean updateProduct(@RequestBody ProductBean productBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            if (productBean != null) {
                response.setStatus(productService.updateProduct(productBean));
            }
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/get-product-inventory", method = RequestMethod.POST)  // 
    @ResponseBody
    public ProductBean getProductInventory(@RequestBody ProductRequistBean requistBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        ProductBean productBean;
        if (admin != null) {
            productBean = productService.getProductInventoryById(requistBean.getProductId());
        } else {
            response1.setStatus(401);
            productBean = new ProductBean();
            productBean.setStatusCode("401");
            productBean.setStatus("Invalid Token.");
        }
        return productBean;
    }

    @RequestMapping(value = "/update-product-inventory", method = RequestMethod.POST)  // 
    @ResponseBody
    public StatusBean updateProductInventory(@RequestBody ProductBean productBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            response.setStatus(productService.updateProductInventory(productBean));
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/get-specifications", method = RequestMethod.POST)  // 
    @ResponseBody
    public ProductDetailBean getProductSpecifications(@RequestBody ProductRequistBean requistBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        ProductDetailBean productDetailBean;
        if (admin != null) {
            productDetailBean = productService.getProductSpecifications(requistBean.getCategoryId());
        } else {
            response1.setStatus(401);
            productDetailBean = new ProductDetailBean();
            productDetailBean.setStatusCode("401");
            productDetailBean.setStatus("Invalid Token.");
        }
        return productDetailBean;
    }

    @RequestMapping(value = "/get-specification-value", method = RequestMethod.POST)  // 
    @ResponseBody
    public SpecificationDto getProductSpecificationValue(@RequestBody ProductRequistBean requistBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        SpecificationDto specificationDto;
        if (admin != null) {
//        getOrderId()
            specificationDto = productService.getProductSpecificationValue(requistBean.getCategoryId());
        } else {
            specificationDto = new SpecificationDto();
            response1.setStatus(401);
            specificationDto.setStatusCode("401");
            specificationDto.setStatus("Invalid Token.");
        }
        return specificationDto;
    }

    //<editor-fold defaultstate="collapsed" desc="Web Crawler">
    @RequestMapping(value = "/load-temp-product-list", method = RequestMethod.POST)  //
    @ResponseBody
    public TransforDto loadTempProduct(@RequestBody StatusBean statusBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        TransforDto transforDto = new TransforDto();
        if (admin != null && statusBean.getStatus() != null) {
            transforDto.setStatusBeans(productService.getTempProducts(statusBean.getStatus())); // statusBean.getStatus() stand for URL
            transforDto.setStatus("200");
        } else {
            response1.setStatus(401);
            transforDto.setStatusCode("401");
            transforDto.setStatus("Invalid Token.");
        }
        return transforDto;
    }

    @RequestMapping(value = "/get-temp-product-list", method = RequestMethod.POST)  //
    @ResponseBody
    public TransforDto getTempProduct(@RequestBody ProductRequistBean requistBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        TransforDto transforDto = new TransforDto();
        if (admin != null) {
            transforDto.setStatusBeans(productService.getTempProductList(requistBean.getStart(), requistBean.getLimit())); // statusBean.getStatus() stand for URL
            transforDto.setStatus("200");
        } else {
            response1.setStatus(401);
            transforDto.setStatusCode("401");
            transforDto.setStatus("Invalid Token.");
        }
        return transforDto;
    }

    @RequestMapping(value = "/save-temp-products", method = RequestMethod.POST)  // crall list of ali express link, store in tempproduct and reletave tables
    @ResponseBody
    public TransforDto saveTempProducts(@RequestBody TransforDto transforDto, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        TransforDto transforDto1 = new TransforDto();
        if (admin != null && transforDto.getStatusBeans() != null && !transforDto.getStatusBeans().isEmpty()) {
            transforDto1.setStatusBeans(productService.loadTempProducts(transforDto.getStatusBeans()));
        } else {
            response1.setStatus(401);
            transforDto1.setStatusCode("401");
            transforDto1.setStatus("Invalid Token.");
        }
        return transforDto1;
    }

    //********
    @RequestMapping(value = "/get-temp-product-lise", method = RequestMethod.POST)  //
    @ResponseBody
    public ProductBeans getTempProductList(@RequestBody ProductRequistBean requistBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        ProductBeans productBeans;
        if (admin != null) {
            productBeans = productService.getAllTempProductsIncloudeZeroAvailable(requistBean.getStart(), requistBean.getLimit());
        } else {
            response1.setStatus(401);
            productBeans = new ProductBeans();
            productBeans.setStatusCode("401");
            productBeans.setStatus("Invalid Token.");
        }
        return productBeans;
    }

    @RequestMapping(value = "/update-temp-product-details", method = RequestMethod.POST)  //
    @ResponseBody
    public StatusBean updateTempProduct(@RequestBody ProductBean productBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            if (productBean != null) {
                response.setStatus(productService.updateTempProduct(productBean));
            }
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/get-temp-product-inventory", method = RequestMethod.POST)  //
    @ResponseBody
    public ProductBean getTempProductInventory(@RequestBody ProductRequistBean requistBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        ProductBean productBean;
        if (admin != null) {
            productBean = productService.getTempProductInventoryById(requistBean.getProductId());
        } else {
            response1.setStatus(401);
            productBean = new ProductBean();
            productBean.setStatusCode("401");
            productBean.setStatus("Invalid Token.");
        }
        return productBean;
    }

    @RequestMapping(value = "/update-temp-product-inventory", method = RequestMethod.POST)  //
    @ResponseBody
    public StatusBean updateTempProductInventory(@RequestBody ProductBean productBean, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        StatusBean response = new StatusBean();
        if (admin != null) {
            response.setStatus(productService.updateTempProductInventory(productBean));
        } else {
            response1.setStatus(401);
            response.setStatusCode("401");
            response.setStatus("Invalid Token.");
        }
        return response;
    }

    @RequestMapping(value = "/commit-product", method = RequestMethod.POST)  // move temp product to main product table
    @ResponseBody
    public TransforDto commitProduct(@RequestBody TransforDto transforDto, HttpServletRequest request, HttpServletResponse response1) {
        DuqhanAdmin admin = adminService.getUserByToken(request.getHeader("X-Auth-Token"));   // Check whether Auth-Token is valid, provided by user
        TransforDto transforDto1 = new TransforDto();
        if (admin != null) {
            transforDto1.setStatusBeans(productService.moveTempProductToProduct(transforDto.getStatusBeans()));
        } else {
            response1.setStatus(401);
            transforDto1.setStatusCode("401");
            transforDto1.setStatus("Invalid Token.");
        }
        return transforDto1;
    }
//</editor-fold>

    @RequestMapping(value = "/test", method = RequestMethod.GET)  // 
    @ResponseBody
    public List<StatusBean> test() {
        List<StatusBean> statusBeans = new ArrayList<>();
        StatusBean statusBean = new StatusBean();
        statusBean.setId(2003l);
        statusBeans.add(statusBean);
        List<StatusBean> beans = productService.loadTempProducts(statusBeans);
        return beans;
    }

}
