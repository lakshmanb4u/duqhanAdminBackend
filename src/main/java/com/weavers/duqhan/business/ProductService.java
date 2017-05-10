/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.business;

import com.weavers.duqhan.dto.CategoryDto;
import com.weavers.duqhan.dto.ColorAndSizeDto;
import com.weavers.duqhan.dto.ProductBean;
import com.weavers.duqhan.dto.ProductBeans;
import com.weavers.duqhan.dto.ProductDetailBean;
import com.weavers.duqhan.dto.SizeDto;
import com.weavers.duqhan.dto.SpecificationDto;
import com.weavers.duqhan.dto.StatusBean;
import java.util.List;

/**
 *
 * @author Android-3
 */
public interface ProductService {

    ColorAndSizeDto getCategories();

    ColorAndSizeDto getSizes();

    ColorAndSizeDto getSizeGroupe();

    ColorAndSizeDto getColors();

    ColorAndSizeDto getVendors();

    ColorAndSizeDto getSpecificationsByCategoryId(Long categoryId);

    String saveProduct(ProductBean productBean);

    String saveCategory(CategoryDto categoryDto);

    String saveSize(SizeDto sizeDto);

    String saveSizeGroup(String sizeGroup);

    String saveColor(String color);

    String saveProductImage(ProductBean productBean);

    String saveSpecification(SpecificationDto specificationDto);

    String saveSpecificationValue(SpecificationDto specificationDto);

    ProductBeans getAllProductsIncloudeZeroAvailable(int start, int limit);

    String updateProduct(ProductBean productBean);

    ProductBean getProductInventoryById(Long productId);

    String updateProductInventory(ProductBean productBean);

    ProductDetailBean getProductSpecifications(Long categoryId);

    SpecificationDto getProductSpecificationValue(Long specificationId);

    List<StatusBean> getTempProducts(String link);

    List<StatusBean> getTempProductList(int start, int limit);

    List<StatusBean> loadTempProducts(List<StatusBean> statusBeans);

    ProductBeans getAllTempProductsIncloudeZeroAvailable(int start, int limit);

    String updateTempProduct(ProductBean productBean);

    ProductBean getTempProductInventoryById(Long productId);

    String updateTempProductInventory(ProductBean productBean);

    List<StatusBean> moveTempProductToProduct(List<StatusBean> statusBeans);

    //**************************************************
    /*ColorAndSizeDto getColorSizeList();

    ProductBeans getProductsByCategory(Long categoryId, int start, int limit);

    ProductBeans getProductsByRecentView(Long userId, int start, int limit);

    ProductBeans getAllProducts(int start, int limit);

    ProductBeans searchProducts(ProductRequistBean requistBean);

    ProductDetailBean getProductDetailsById(Long productId, Long userId);

    Long getCartCountFoAUser(Long userId);

    String addProductToCart(ProductRequistBean requistBean);

    String removeProductFromCart(ProductRequistBean requistBean);

    CartBean getCartForUser(Long userId);

    CategorysBean getChildById(Long parentId);

    OrderDetailsBean getOrderDetails(Long userId, int start, int limit);

    void cancelOrder(String orderId, Long userId);*/
}
