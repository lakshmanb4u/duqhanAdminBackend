/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Android-3
 */
public class DateFormater {

    public static String formate(Date date, String formate) {
        formate = "dd/MM/yyyy"; //it is fixed for all project
        String formatedDate = null;
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(formate);
                formatedDate = sdf.format(date);
            } catch (Exception e) {
                return null;
            }
        }
        return formatedDate;
    }
    
    public static String formate(Date date) {
        String formate = "dd/MM/yyyy"; //it is fixed for all project
        String formatedDate = null;
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(formate);
                formatedDate = sdf.format(date);
            } catch (Exception e) {
                return null;
            }
        }
        return formatedDate;
    }

    public static Date formateToDate(String date) {
        Date formatedDate = null;
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                formatedDate = sdf.parse(date);
            } catch (Exception e) {
                System.out.println("Exceptioneeeeeeeeee" + e.getMessage());
                return null;
            }
        }
        return formatedDate;
    }
}
