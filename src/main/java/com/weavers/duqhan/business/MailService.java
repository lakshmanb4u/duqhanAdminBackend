/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.business;

import com.weavers.duqhan.domain.OrderDetails;

/**
 *
 * @author weaversAndroid
 */
public interface MailService {

    String mailToUserForOrderStatusChange(OrderDetails orderDetails);
}
