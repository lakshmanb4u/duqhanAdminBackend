/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.util;

import com.weavers.duqhan.dao.CartDao;
import com.weavers.duqhan.dao.ProductDao;
import com.weavers.duqhan.dao.ProductSizeColorMapDao;
import com.weavers.duqhan.domain.Cart;
import com.weavers.duqhan.domain.Product;
import com.weavers.duqhan.domain.ProductSizeColorMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author weaversAndroid
 */
public class Scheduler {

    @Autowired
    CartDao cartDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    ProductSizeColorMapDao productSizeColorMapDao;

//    @Scheduled(cron = "*/25 * * * * *")
//    @Scheduled(cron = "0 0 0/2 * * ?")
    void checkAvailability() {  //check availability of product in cart.
        Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, "checkAvailability scheduler start ========");
        List<Long> mapIds = new ArrayList<>();
        List<Cart> carts = cartDao.loadAll();
        System.out.println("carts == " + carts.size());
        if (!carts.isEmpty()) {
            for (Cart cart : carts) {
                mapIds.add(cart.getSizecolormapId());
            }
            List<ProductSizeColorMap> productSizeColorMaps = productSizeColorMapDao.loadByIds(mapIds);
            System.out.println("productSizeColorMaps == " + productSizeColorMaps.size());
            for (ProductSizeColorMap productSizeColorMap : productSizeColorMaps) {
                Product product = productDao.loadById(productSizeColorMap.getProductId());
                try {
                    Random randomObj = new Random();
                    TimeUnit.SECONDS.sleep(randomObj.ints(30, 60).findFirst().getAsInt());
                    Document doc = Jsoup.connect(product.getExternalLink()).get();
                    Elements detailMain = doc.select("#j-detail-page");
                    if (detailMain.isEmpty()) {
                        productSizeColorMap.setQuentity(0L);
                        productSizeColorMapDao.save(productSizeColorMap);
                    }
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
