package com.weavers.duqhan.dto;

import java.util.List;

/**
 *
 * @author Android-3
 */
public class ProductNewBeans {
	private List<ProductNewBean> products;
    private String statusCode;
    private String status;
    private String searchString;
	public List<ProductNewBean> getProducts() {
		return products;
	}
	public void setProducts(List<ProductNewBean> products) {
		this.products = products;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
    
}