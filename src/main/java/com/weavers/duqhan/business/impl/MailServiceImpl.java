/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.business.impl;

import com.weavers.duqhan.business.MailService;
import com.weavers.duqhan.dao.CategoryDao;
import com.weavers.duqhan.dao.ProductDao;
import com.weavers.duqhan.dao.ProductPropertiesDao;
import com.weavers.duqhan.dao.ProductPropertiesMapDao;
import com.weavers.duqhan.dao.ProductPropertyvaluesDao;
import com.weavers.duqhan.dao.UserAddressDao;
import com.weavers.duqhan.dao.UsersDao;
import com.weavers.duqhan.domain.Category;
import com.weavers.duqhan.domain.OrderDetails;
import com.weavers.duqhan.domain.Product;
import com.weavers.duqhan.domain.ProductProperties;
import com.weavers.duqhan.domain.ProductPropertiesMap;
import com.weavers.duqhan.domain.ProductPropertyvalues;
import com.weavers.duqhan.domain.UserAddress;
import com.weavers.duqhan.domain.Users;
import com.weavers.duqhan.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author weaversAndroid
 */
public class MailServiceImpl implements MailService {

    @Autowired
    UserAddressDao userAddressDao;
    @Autowired
    ProductPropertyvaluesDao productPropertyvaluesDao;
    @Autowired
    ProductPropertiesDao productPropertiesDao;
    @Autowired
    ProductPropertiesMapDao productPropertiesMapDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    UsersDao usersDao;

    private static final String ADMIN_MAIL = "mamidi.laxman.lnu@gmail.com";//duqhanapp@gmail.com
    private static final String BCC = "krisanu.nandi@pkweb.in";

    @Override
    public String mailToUserForOrderStatusChange(OrderDetails orderDetails) {
        if (null != orderDetails) {
            String body = "";
            Users user = usersDao.loadById(orderDetails.getUserId());
            long addressId = orderDetails.getAddressId();
            UserAddress address = userAddressDao.loadById(addressId);
            String addressPath = "                             <tr> "
                    + "                                        <td style=\"font-size:14px;padding:11px 18px 18px 18px;background-color:rgb(250,244,237);width:50%;vertical-align:top;line-Your order willheight:18px;font-family:Arial,sans-serif\"> "
                    + "                                            <p style=\"margin:2px 0 9px 0;font:14px Arial,sans-serif\"> <span style=\"font-size:14px;color:rgb(89,145,57)\">Your order " + (orderDetails.getStatus().equals("delivered") ? "has delivered" : "will be sent") + " to:</span><br> "
                    + "                                                <b> " + user.getName() + " <br>"
                    + address.getStreetOne() + "," + (address.getStreetTwo() != null ? address.getStreetTwo() : "") + "<br>"
                    + address.getCity() + " " + address.getState() + " " + address.getZipCode() + "<br>"
                    + "                                                    India <br>" + address.getPhone()
                    + "                                                </b>"
                    + "                                            </p> "
                    + "                                        </td> "
                    + "                                    </tr> ";
            Product product;
            ProductPropertiesMap propertyMap;
            String order = "";
            Category category;
            propertyMap = productPropertiesMapDao.loadById(orderDetails.getMapId());
            String property = "";
            String[] propertyValueIds = new String[0];
            propertyValueIds = propertyMap.getPropertyvalueComposition().split("_");
            if (propertyValueIds.length > 0) {
                property += "";
                for (String propertyValueId : propertyValueIds) {
                    try {
                        ProductPropertyvalues productPropertyvalues = productPropertyvaluesDao.loadById(Long.valueOf(propertyValueId));
//                        ProductProperties properties = productPropertiesDao.loadById(productPropertyvalues.getPropertyId());
                        property = property + productPropertyvalues.getValueName() + ", ";
                    } catch (Exception nfe) {
                    }
                }
                property += "";
            }
            product = propertyMap.getProductId()/*productDao.loadById(propertyMap.getProductId())*/;
            category = categoryDao.loadById(product.getCategoryId());
            order = order + "                               <tr>"
                    + "                                        <td><img src=\"" + product.getImgurl() + "\"  alt=\"Duqhan\" style=\"border:0;width:115px\"/></td>"
                    + "                                        <td>"
                    + "                                            <table style=\"width:100%;border-collapse:collapse\">"
                    + "                                                <tr>"
                    + "                                                    <td>" + product.getName() + "</td>"
                    + "                                                </tr>"
                    + "                                                <tr>"
                    + "                                                    <td>" + category.getName() + property + "</td>"
                    + "                                                </tr>"
                    + "                                                <tr>"
                    + "                                                    <td style=\"color:rgb(0,102,153);font:Arial,sans-serif\">#" + orderDetails.getOrderId() + "</td>"
                    + "                                                </tr>"
                    + "                                            </table>"
                    + "                                        </td>"
                    + "                                        <td>Rs." + orderDetails.getPaymentAmount() + "</td>"
                    + "                                    </tr>";

            body = "<table style=\"width:100%;border-collapse:collapse\">"
                    + "    <tr>"
                    + "        <td style=\"padding:0 20px 20px 20px;vertical-align:top;font-size:13px;line-height:18px;font-family:Arial,sans-serif\">"
                    + "            <table style=\"width:100%;border-collapse:collapse\">"
                    + "                <thead>"
                    + "                    <tr>"
                    + "                        <td><img src=\"https://storage.googleapis.com/duqhan-users/logo.png\"  alt=\"Duqhan\" src=\"\" style=\"border:0;width:100px\" /><hr></td>"
                    + "                    </tr>"
                    + "                </thead>"
                    + "                <tbody>"
                    + "                    <tr>"
                    + "                        <td><h3 style=\"font-size:18px;color:rgb(204,102,0);margin:15px 0 0 0;font-weight:normal\">Hello " + user.getName() + ",</h3></td>"
                    + "                    </tr>"
                    + "                    <tr>"
                    + "                        <td><span>Thank you for your order.</span></td>"
                    + "                    </tr>"
                    + "                    <tr>"
                    + "                        <td>"
                    + "                            <table style=\"border-top:3px solid rgb(250,144,5);width:95%;border-collapse:collapse\">"
                    + "                                <tbody>"
                    + addressPath
                    + "                                </tbody>"
                    + "                            </table>"
                    + "                        </td>"
                    + "                    </tr>"
                    + "                    <tr>"
                    + "                        <td><h3 style=\"font-size:18px;color:rgb(204,102,0);margin:15px 0 0 0;font-weight:normal\">Order Details (" + (orderDetails.getStatus()) + ")</h3></td>"
                    + "                    </tr>"
                    + "                    <tr>"
                    + "                        <td>"
                    + "                            <table style=\"width:100%;border-collapse:collapse\">"
                    + "                                <tbody>"
                    + order
                    + "                                </tbody>"
                    + "                            </table>"
                    + "                        </td>"
                    + "                    </tr>"
                    + "                </tbody>"
                    + "            </table>"
                    + "        </td>"
                    + "    </tr>"
                    + "</table>";
            String status = MailSender.sendEmail(user.getEmail(), "Order status", body, ADMIN_MAIL + "," + "");// send mail to User at order status change.
            return status;
        } else {
            return "fail";
        }
    }

}
