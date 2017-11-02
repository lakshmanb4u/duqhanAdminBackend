/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weavers.duqhan.dto.CurrencyRates;
import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author weaversAndroid
 */
public class CurrencyConverter {

    public static Double convert(String currencyFrom, String currencyTo) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet("https://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote?format=json");
        HttpGet httpGet = new HttpGet("https://cdn.shopify.com/s/javascripts/currencies.js");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = httpclient.execute(httpGet, responseHandler);
        httpclient.getConnectionManager().shutdown();

        String rates = responseBody.split("rates:")[1].split("convert")[0];
        rates = rates.substring(0, rates.length() - 1);
        ObjectMapper mapper = new ObjectMapper();
        CurrencyRates jSONReader = null;
        jSONReader = mapper.readValue(rates, CurrencyRates.class);
//        System.out.println("ssssssssssss == " + jSONReader.getINR());
        double ratio = 0.0;
        if (currencyTo.equals("USD")) {
            ratio = jSONReader.getINR() / jSONReader.getUSD();
        } else {
            ratio = jSONReader.getUSD() / jSONReader.getINR();
        }
        return ratio;
    }

    public static Double usdTOinr(Double usdValue) {
        try {
//            amount * this.rates[from]) / this.rates[to]

            Double inrRatio = CurrencyConverter.convert("USD", "INR");//usd to inr
            return usdValue * inrRatio;
        } catch (Exception e) {
            return null;
        }
    }

    public static Double inrTOusd(Double inrValue) {
        try {
            Double usdRatio = CurrencyConverter.convert("INR", "USD");//inr to usd
            return inrValue * usdRatio;
        } catch (Exception e) {
            return null;
        }
    }

//    public static void main(String[] args) {
//        CurrencyConverter ycc = new CurrencyConverter();
//        try {
//            System.out.println(ycc.inrTOusd(700.0));
//            System.out.println(ycc.usdTOinr(ycc.inrTOusd(700.0)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
