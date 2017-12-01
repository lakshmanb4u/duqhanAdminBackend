/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.dto;

import java.util.List;

/**
 *
 * @author Android-3
 */
public class SizeDto {

    private Long sizeId;
    private String sizeText;
    private Long sizeGroupId;
    private List<SizeColorMapDto> sizeColorMap;

    /**
     * @return the sizeId
     */
    public Long getSizeId() {
        return sizeId;
    }

    /**
     * @param sizeId the sizeId to set
     */
    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    /**
     * @return the sizeText
     */
    public String getSizeText() {
        return sizeText;
    }

    /**
     * @param sizeText the sizeText to set
     */
    public void setSizeText(String sizeText) {
        this.sizeText = sizeText;
    }

    /**
     * @return the sizeColorMap
     */
    public List<SizeColorMapDto> getSizeColorMap() {
        return sizeColorMap;
    }

    /**
     * @param sizeColorMap the sizeColorMap to set
     */
    public void setSizeColorMap(List<SizeColorMapDto> sizeColorMap) {
        this.sizeColorMap = sizeColorMap;
    }

    /**
     * @return the sizeGroupId
     */
    public Long getSizeGroupId() {
        return sizeGroupId;
    }

    /**
     * @param sizeGroupId the sizeGroupId to set
     */
    public void setSizeGroupId(Long sizeGroupId) {
        this.sizeGroupId = sizeGroupId;
    }
}
