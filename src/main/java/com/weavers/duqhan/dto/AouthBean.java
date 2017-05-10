/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dto;

import java.util.Date;

/**
 *
 * @author Android-3
 */
public class AouthBean {

    private long userId;
    private String aouthToken;
    private Date validTill;
    private String status;

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the aouthToken
     */
    public String getAouthToken() {
        return aouthToken;
    }

    /**
     * @param aouthToken the aouthToken to set
     */
    public void setAouthToken(String aouthToken) {
        this.aouthToken = aouthToken;
    }

    /**
     * @return the validTill
     */
    public Date getValidTill() {
        return validTill;
    }

    /**
     * @param validTill the validTill to set
     */
    public void setValidTill(Date validTill) {
        this.validTill = validTill;
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
