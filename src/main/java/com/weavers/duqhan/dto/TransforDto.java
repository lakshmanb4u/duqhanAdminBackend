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
public class TransforDto {

    private List<StatusBean> statusBeans;
    private String statusCode;
    private String status;

    /**
     * @return the statusBeans
     */
    public List<StatusBean> getStatusBeans() {
        return statusBeans;
    }

    /**
     * @param statusBeans the statusBeans to set
     */
    public void setStatusBeans(List<StatusBean> statusBeans) {
        this.statusBeans = statusBeans;
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
}
