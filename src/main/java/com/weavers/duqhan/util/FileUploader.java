/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.weavers.duqhan.dto.CloudineryImageDto;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Android-3
 */
public class FileUploader {

    private static final String CLOUD_NAME = "duqhan";
    private static final String API_KEY = "211572778157664";
    private static final String API_SECRET = "BjqvouftX41P4NHFbAEPFaBWFog";

    public static CloudineryImageDto uploadImage(MultipartFile file) {
        CloudineryImageDto imageBean = new CloudineryImageDto();
        imageBean.setUrl("failure");
        String timeInMili = String.valueOf(new Date().getTime());
        Map params = Cloudinary.asMap("public_id", timeInMili);
        Calendar calendar = Calendar.getInstance();
        List<Transformation> eager = Arrays.asList(new Transformation().width(512).height(512).crop("thumb"));
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "tags", "product",
                "timestamp", calendar.getTimeInMillis(),
                "api_secret", API_SECRET,
                // "upload_preset", "gpucdhrn",
                //  "transformation", incoming,
                "eager", eager
        ));

        Map uploadResult;
        String url = null;
        try {
//            cloudinary.url().type("fetch").imageTag("http://upload.wikimedia.org/wikipedia/commons/0/0c/Scarlett_Johansson_Césars_2014.jpg");
            byte[] file1 = file.getBytes();
            uploadResult = cloudinary.uploader().upload(file1, params);
//            cloudinary.url().transformation(new Transformation().width(512).height(512).crop("fill")).imageTag(params);
            String publicId = (String) uploadResult.get("public_id");
            url = (String) uploadResult.get("url");
            String signature = (String) uploadResult.get("signature");
            String format = (String) uploadResult.get("format");
            String secureUrl = (String) uploadResult.get("secure_url");
            Integer version = (Integer) uploadResult.get("version");

            //<editor-fold defaultstate="collapsed" desc="Image Bean">
            imageBean.setFormat(format);
            imageBean.setPublicId(publicId);
            imageBean.setSecureUrl(secureUrl);
            imageBean.setSignature(signature);
            imageBean.setVersion(Long.valueOf(version));
            imageBean.setUrl(url);
//</editor-fold>

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FileUploader.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("eeeeeeeeeee"+ex.getLocalizedMessage());
            imageBean.setUrl("failure");
        }
        return imageBean;
    }

    public static CloudineryImageDto uploadImage(String imgUrl) {
        CloudineryImageDto imageBean = new CloudineryImageDto();
        imageBean.setUrl("failure");
        String timeInMili = String.valueOf(new Date().getTime());
        Map params = Cloudinary.asMap("public_id", timeInMili);
        Calendar calendar = Calendar.getInstance();
        List<Transformation> eager = Arrays.asList(new Transformation().width(512).height(512).crop("thumb"));
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "tags", "product",
                "timestamp", calendar.getTimeInMillis(),
                "api_secret", API_SECRET,
                // "upload_preset", "gpucdhrn",
                //  "transformation", incoming,
                "eager", eager
        ));

        Map uploadResult;
        String url = null;
        try {
//            cloudinary.url().type("fetch").imageTag("http://upload.wikimedia.org/wikipedia/commons/0/0c/Scarlett_Johansson_Césars_2014.jpg");
            uploadResult = cloudinary.uploader().upload(imgUrl, ObjectUtils.emptyMap());
            String publicId = (String) uploadResult.get("public_id");
            url = (String) uploadResult.get("url");
            System.out.println("url == " + url);
            String signature = (String) uploadResult.get("signature");
            String format = (String) uploadResult.get("format");
            String secureUrl = (String) uploadResult.get("secure_url");
            Integer version = (Integer) uploadResult.get("version");

            //<editor-fold defaultstate="collapsed" desc="Image Bean">
            imageBean.setFormat(format);
            imageBean.setPublicId(publicId);
            imageBean.setSecureUrl(secureUrl);
            imageBean.setSignature(signature);
            imageBean.setVersion(Long.valueOf(version));
            imageBean.setUrl(url);
//</editor-fold>

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FileUploader.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("eeeeeeeeeee"+ex.getLocalizedMessage());
            imageBean.setUrl("failure");
        }
        return imageBean;
    }

//    public static void main(String[] args) {
//        uploadImage("https://ae01.alicdn.com/kf/HTB1UjEAJFXXXXasaXXXq6xXFXXXf/10pcs-Handmade-Ox-Pendant-cute-women-Lovely-Ox-Pendant-Anniversary-Birthday-Christmas-jwelry-Gift.jpg");
//    }
}
