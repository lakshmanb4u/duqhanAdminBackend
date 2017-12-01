/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Android-3
 */
public class ProductDetailBean {

    private Long productId;
    private String name;
    private String description;
    private Long categoryId;
    private String categoryName;
    private String productImg;
    private Double orginalPrice;
    private Double salesPrice;
    private Double discount;
    private List<ImageDto> images;
    private Set<SizeDto> sizes;
    private Set<ColorDto> colors;
    private String arrival;
    private Double shippingCost;
    private Long vendorId;
    private String shippingTime;
    private ProductBeans relatedProducts;
    private String statusCode;
    private String status;
    private HashMap<String, String> specifications;

    /**
     * @return the productId
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the orginalPrice
     */
    public Double getOrginalPrice() {
        return orginalPrice;
    }

    /**
     * @param orginalPrice the orginalPrice to set
     */
    public void setOrginalPrice(Double orginalPrice) {
        this.orginalPrice = orginalPrice;
    }

    /**
     * @return the salesPrice
     */
    public Double getSalesPrice() {
        return salesPrice;
    }

    /**
     * @param salesPrice the salesPrice to set
     */
    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    /**
     * @return the discount
     */
    public Double getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    /**
     * @return the images
     */
    public List<ImageDto> getImages() {
        return images;
    }

    /**
     * @param images the images to set
     */
    public void setImages(List<ImageDto> images) {
        this.images = images;
    }

    /**
     * @return the sizes
     */
    public Set<SizeDto> getSizes() {
        return sizes;
    }

    /**
     * @param sizes the sizes to set
     */
    public void setSizes(Set<SizeDto> sizes) {
        this.sizes = sizes;
    }

    /**
     * @return the colors
     */
    public Set<ColorDto> getColors() {
        return colors;
    }

    /**
     * @param colors the colors to set
     */
    public void setColors(Set<ColorDto> colors) {
        this.colors = colors;
    }

    /**
     * @return the arrival
     */
    public String getArrival() {
        return arrival;
    }

    /**
     * @param arrival the arrival to set
     */
    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    /**
     * @return the shippingCost
     */
    public Double getShippingCost() {
        return shippingCost;
    }

    /**
     * @param shippingCost the shippingCost to set
     */
    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    /**
     * @return the relatedProducts
     */
    public ProductBeans getRelatedProducts() {
        return relatedProducts;
    }

    /**
     * @param relatedProducts the relatedProducts to set
     */
    public void setRelatedProducts(ProductBeans relatedProducts) {
        this.relatedProducts = relatedProducts;
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the categoryId
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the productImg
     */
    public String getProductImg() {
        return productImg;
    }

    /**
     * @param productImg the productImg to set
     */
    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    /**
     * @return the vendorId
     */
    public Long getVendorId() {
        return vendorId;
    }

    /**
     * @param vendorId the vendorId to set
     */
    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * @return the shippingTime
     */
    public String getShippingTime() {
        return shippingTime;
    }

    /**
     * @param shippingTime the shippingTime to set
     */
    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    /**
     * @return the specifications
     */
    public HashMap<String, String> getSpecifications() {
        return specifications;
    }

    /**
     * @param specifications the specifications to set
     */
    public void setSpecifications(HashMap<String, String> specifications) {
        this.specifications = specifications;
    }
}
