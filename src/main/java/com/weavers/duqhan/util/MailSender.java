/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weavers.duqhan.util;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author Android-3
 */
public class MailSender {

    private static final String username = "duqhanapp@gmail.com";
    private static final String password = "duqhanapp123";

    public static String sendEmail(String destinationAddress, String subject, String msg, String bcc_address) /*throws Exception*/ {
        String status = "success";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            // Make a new message
            MimeMessage message = new MimeMessage(session);

            // Who is this message from
            message.setFrom(new InternetAddress(username, "Duqhan"));

            // Who is this message to (we could do fancier things like make a list or add CC's)
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinationAddress));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc_address));

            // Subject and body
            message.setSubject(subject);
            // message.setText(msg);
            message.setContent(msg, "text/html; charset=ISO-8859-1");

            // We can do more here, set the date, the headers, etc.
            Transport.send(message);
            System.out.println("Mail sent!");
        } catch (UnsupportedEncodingException | MessagingException e) {
            status = "faild";
            System.out.println("MAIL SEND API 465");
            e.printStackTrace();
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, "MAIL SEND API 465", e);
//            throw new UtilityError("unable to send email");

        }
        return status;
    }
//    public static void main(String[] args) {
//        MailSender.sendEmail("santanu55.bankura@gmail.com","test" , "hi....", "subhendu.sett@pkweb.in");
//    }
}
