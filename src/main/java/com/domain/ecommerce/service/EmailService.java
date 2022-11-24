package com.domain.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Candelario Aguilar Torres
 **/
@Service
public class EmailService {

    private JavaMailSender emailSender;
    @Autowired
    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;

    }
   @Async
    public void sendMessage(String to,String tempToken) {

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message,"utf-8");
        try {
            messageHelper.setTo(to);
            messageHelper.setReplyTo("noReply@mobilemerchants.com");
            message.setSubject("Password Reset Request");
            messageHelper.setText("<html>\n" +
                    "    <head>\n" +
                    "    <meta charset=\"utf-8\">\n" +
                    "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                    "    </head>\n" +
                    "\n" +
                    "    <body style=\"background-color: lightgray;\">\n" +
                    "        \n" +
                    "       \n" +
                    "        \n" +
                    "            <div style=\"margin: 50px;background-color: white;padding: 10px; \">\n" +
                    "            \n" +
                    "              <h2 style=\"text-align: left;font-size:22px;font-family:Helvetica;color:#14468a\">Reset Password</h2>\n" +
                    "            \n" +
                    "    \n" +
                    "              <p style=\"margin-bottom: 50px;font-size:16px;\">You have requested a password reset for your Mobile Merchants account associated with the email " + to + ". Visit the link below to reset your password.</p>\n" +
                    "              <div>\n" +
                    "                <a href style=\"text-decoration: none; font-size: 16px;border-radius:5px;background-color:#14468a; border: none; padding-top:15px;padding-bottom:15px;padding-left:35px;padding-right:35px;color: white;font-family:Helvetica\" type=\"button\" href=http://127.0.0.1:8000/Account/resetpassword.html??token=" + tempToken +">Reset Password</a>\n" +
                    "              </div>\n" +
                    "\n" +
                    "              <div>\n" +
                    "                <p style=\"margin-top: 50px;\">If you did not request a password reset, please ignore this email.</p>\n" +
                    "              </div>\n" +
                    "            \n" +
                    "            <!--#14468a color-->\n" +
                    "            </div>\n" +
                    "          \n" +
                    "       \n" +
                    "</body>\n" +
                    "</html>",true);


            emailSender.send(message);



        } catch (MessagingException e) {
            System.out.println("____________________________________________________________________________________________");
            System.out.println("COULD NOT SEND EMAIL");
        }


    }
}
