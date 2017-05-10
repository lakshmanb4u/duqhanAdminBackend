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
public class CloudineryImageDto {

    private String publicId;
    private Long version;
    private String signature;
    private Long width;
    private Long height;
    private String format;
    private String resourceType;
    private String createdAt;
    private Long bytes;
    private String type;
    private String url;
    private String secureUrl;

    /**
     * @return the publicId
     */
    public String getPublicId() {
        return publicId;
    }

    /**
     * @param publicId the publicId to set
     */
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    /**
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature the signature to set
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @return the width
     */
    public Long getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(Long width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public Long getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(Long height) {
        this.height = height;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return the resourceType
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * @param resourceType the resourceType to set
     */
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * @return the createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the bytes
     */
    public Long getBytes() {
        return bytes;
    }

    /**
     * @param bytes the bytes to set
     */
    public void setBytes(Long bytes) {
        this.bytes = bytes;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the secureUrl
     */
    public String getSecureUrl() {
        return secureUrl;
    }

    /**
     * @param secureUrl the secureUrl to set
     */
    public void setSecureUrl(String secureUrl) {
        this.secureUrl = secureUrl;
    }
}
