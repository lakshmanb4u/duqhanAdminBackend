/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dto;

/**
 *
 * @author Android-3
 */
public class ProductRequistBean {

    private Long categoryId;
    private Long productId;
    private Long userId;
    private Boolean isRecent;
    private Long mapId;
    private Long quantity;
    private Long cartId;
    private String name;
    private String orderId;
    private int start;
    private int limit;
    private double discountOfferPct;
    private String priceOrderBy;
    private Integer priceGt;
    private Integer priceLt;
    private String lowPrice;
    private String highPrice;
    private String loadCategory;

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
     * @return the isRecent
     */
    public Boolean getIsRecent() {
        return isRecent;
    }

    /**
     * @param isRecent the isRecent to set
     */
    public void setIsRecent(Boolean isRecent) {
        this.isRecent = isRecent;
    }

    /**
     * @return the mapId
     */
    public Long getMapId() {
        return mapId;
    }

    /**
     * @param mapId the mapId to set
     */
    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    /**
     * @return the quantity
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the cartId
     */
    public Long getCartId() {
        return cartId;
    }

    /**
     * @param cartId the cartId to set
     */
    public void setCartId(Long cartId) {
        this.cartId = cartId;
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
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return the discountOfferPct
     */
    public double getDiscountOfferPct() {
        return discountOfferPct;
    }

    /**
     * @param discountOfferPct the discountOfferPct to set
     */
    public void setDiscountOfferPct(double discountOfferPct) {
        this.discountOfferPct = discountOfferPct;
    }

	public String getPriceOrderBy() {
		return priceOrderBy;
	}

	public void setPriceOrderBy(String priceOrderBy) {
		this.priceOrderBy = priceOrderBy;
	}

	public Integer getPriceGt() {
		return priceGt;
	}

	public void setPriceGt(Integer priceGt) {
		this.priceGt = priceGt;
	}

	public Integer getPriceLt() {
		return priceLt;
	}

	public void setPriceLt(Integer priceLt) {
		this.priceLt = priceLt;
	}

	public String getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(String highPrice) {
		this.highPrice = highPrice;
	}

	public String getLoadCategory() {
		return loadCategory;
	}

	public void setLoadCategory(String loadCategory) {
		this.loadCategory = loadCategory;
	}
	
	
   
}
