/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.util;

import com.weavers.duqhan.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author weaversAndroid
 */
public class Scheduler {


    @Autowired
    ProductDao productDao;

//    @Scheduled(cron = "*/25 * * * * *")
//    @Scheduled(cron = "0 0 0/2 * * ?")
    void checkAvailability() {  //check availability of product in cart.
        /*Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, "checkAvailability scheduler start ========");
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
        }*/
    }
}
