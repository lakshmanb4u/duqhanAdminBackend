/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dto;

import java.util.List;

/**
 *
 * @author weaversAndroid
 */
public class OrderListDto {

    private List<OrderDto> orderDtos;
    private int start;
    private int limit;
    private String status;
    private String StatusCode;

    /**
     * @return the orderDtos
     */
    public List<OrderDto> getOrderDtos() {
        return orderDtos;
    }

    /**
     * @param orderDtos the orderDtos to set
     */
    public void setOrderDtos(List<OrderDto> orderDtos) {
        this.orderDtos = orderDtos;
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
     * @return the StatusCode
     */
    public String getStatusCode() {
        return StatusCode;
    }

    /**
     * @param StatusCode the StatusCode to set
     */
    public void setStatusCode(String StatusCode) {
        this.StatusCode = StatusCode;
    }
}
