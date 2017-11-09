/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.util;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author weaversAndroid
 */
public class GoogleBucketFileUploader {
    // https://cloud.google.com/java/getting-started/using-cloud-storage

    private static final String PROJECT_ID = "PROJECT_ID";
    private static final String USER_BUCKET_NAME = "USER_BUCKET_NAME";
    private static final String PRODUCT_BUCKET_NAME = "PRODUCT_BUCKET_NAME";
    private static final String OLD_PRODUCT_BUCKET_NAME = "duqhan-images";
    private static final String JSON_PATH = "/DUQHAN-e19d56eacc29.json";

    private Storage authentication() {
        Storage storage = null;
        try {
            InputStream configStream = this.getClass().getResourceAsStream(JSON_PATH);
            storage = StorageOptions.newBuilder()
                    .setProjectId(PROJECT_ID)
                    .setCredentials(ServiceAccountCredentials.fromStream(configStream))
                    // for absolute local drive path 
                    //.setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("C://Users/weaversAndroid/Downloads/DUQHAN-e19d56eacc29.json")))
                    .build()
                    .getService();
        } catch (IOException ex) {
            Logger.getLogger(GoogleBucketFileUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return storage;
    }

    public static String uploadProductImage(MultipartFile file, Long productId) {
        InputStream targetStream = null;
        String imgUrl = "failure";
        if (file != null) {
            try {
                GoogleBucketFileUploader fileUploader = new GoogleBucketFileUploader();
                Storage storage = fileUploader.authentication();
                targetStream = file.getInputStream();
                if (targetStream != null) {
                    DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
                    DateTime dt = DateTime.now(DateTimeZone.UTC);
                    String dtString = dt.toString(dtf);
                    final String fileName = "img_" + productId.toString() + dtString + ".jpg";
                    // the inputstream is closed by default, so we don't need to close it here
                    BlobInfo blobInfo = storage.create(BlobInfo
                            .newBuilder(PRODUCT_BUCKET_NAME, fileName)
                            .setContentType("image/jpeg")
                            // Modify access list to allow all users with link to read file
                            .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.OWNER))))
                            .build(),
                            targetStream);
                    // return the public view link
//                    System.out.println("https://storage.googleapis.com/duqhan-users/" + blobInfo.getName());
                    imgUrl = "https://storage.googleapis.com/duqhan-images/" + blobInfo.getName();
                }
            } catch (IOException ex) {
                Logger.getLogger(GoogleBucketFileUploader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return imgUrl;
    }

    public static String uploadProductImage(String url, Long productId) {
        String imgUrl = "failure";
        try {
            GoogleBucketFileUploader fileUploader = new GoogleBucketFileUploader();
            Storage storage = fileUploader.authentication();
            InputStream input = new URL(url).openStream();
            if (input != null) {
                DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
                DateTime dt = DateTime.now(DateTimeZone.UTC);
                String dtString = dt.toString(dtf);
                final String fileName = "img_" + productId.toString() + dtString + ".jpg";
                // the inputstream is closed by default, so we don't need to close it here
                BlobInfo blobInfo = storage.create(BlobInfo
                        .newBuilder(PRODUCT_BUCKET_NAME, fileName)
                        .setContentType("image/jpeg")
                        // Modify access list to allow all users with link to read file
                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.OWNER))))
                        .build(),
                        input);
                // return the public view link
                imgUrl = "https://storage.googleapis.com/" + PRODUCT_BUCKET_NAME + "/" + blobInfo.getName();
//                imgUrl = "https://storage.googleapis.com/duqhan-images/" + blobInfo.getName();
            }
        } catch (Exception ex) {
            Logger.getLogger(GoogleBucketFileUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imgUrl;
    }

    public static boolean deleteProductImg(String imgName) {
        boolean status = false;
        try {
            GoogleBucketFileUploader fileUploader = new GoogleBucketFileUploader();
            Storage storage = fileUploader.authentication();
            status = storage.delete(PRODUCT_BUCKET_NAME, imgName);
        } catch (Exception ex) {
            Logger.getLogger(GoogleBucketFileUploader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public static void main(String[] args) {
//        File file = new File("C://Users/weaversAndroid/Desktop/ali/42748.jpg");
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile("file",
//                file.getName(), "image/jpeg", IOUtils.toByteArray(input));
//        uploadProfileImage(multipartFile, 5l);
    }
}
