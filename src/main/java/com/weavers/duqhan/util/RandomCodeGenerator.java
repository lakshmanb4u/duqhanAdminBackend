/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.util;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Android-3
 */
public class RandomCodeGenerator {

    public static String getAlphaNumericCode(int range) {
        String randString = "";
        StringBuffer sb = new StringBuffer();
        String block = "abcdefghijklmnopqrstuvwxyz1234567890ABCDEFIJKLMNOPQRSTUVWXYZ";
        sb.append(block).append(block.toUpperCase()).append("0123456789");
        block = sb.toString();
        sb = new StringBuffer();
        Random random = new Random();
        try {
            for (int i = 0; i < range; i++) {
                sb.append(Character.toString(block.charAt(random.nextInt(block.length() - 1))));
            }
            randString = sb.toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.getLogger(RandomCodeGenerator.class.getName()).log(Level.SEVERE, null, e);
        } catch (NumberFormatException e) {
            Logger.getLogger(RandomCodeGenerator.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            Logger.getLogger(RandomCodeGenerator.class.getName()).log(Level.SEVERE, null, e);
        }
        return randString;
    }

    public static String getNumericCode(int range) {
        String randString = "";
        StringBuffer sb = new StringBuffer();
        String block = "1234567890";
        sb.append(block).append(block.toUpperCase()).append("0123456789");
        block = sb.toString();
        sb = new StringBuffer();
        Random random = new Random();
        try {
            for (int i = 0; i < range; i++) {
                sb.append(Character.toString(block.charAt(random.nextInt(block.length() - 1))));
            }
            randString = sb.toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.getLogger(RandomCodeGenerator.class.getName()).log(Level.SEVERE, null, e);
        } catch (NumberFormatException e) {
            Logger.getLogger(RandomCodeGenerator.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception e) {
            Logger.getLogger(RandomCodeGenerator.class.getName()).log(Level.SEVERE, null, e);
        }
        return randString;
    }

//    public static void main(String[] args) {
//        System.out.println("code is = "+RandomCodeGenerator.getNumericCode(6));
//    }
}
