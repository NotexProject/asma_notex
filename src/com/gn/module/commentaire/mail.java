/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gn.module.commentaire;
import java.util.Properties ; 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author Asma Srairi
 */
public class mail {
    public static void sendMail (String recepient) throws MessagingException {
        System.out.println("Preparing");
   Properties properties  = new Properties () ; 
            properties.put("mail.smtp.auth" ,"true") ; 
            properties.put("mail.smtp.starttls.enable" ,"true") ; 
            properties.put("mail.smtp.host" ,"smtp.gmail.com") ;
            properties.put("mail.smtp.port" ,"587") ; 
            
            String myAccountEmail ="asma.srairi007@gmail.com" ; 
            String password ="assoum007" ; 
            Session session = Session.getInstance(properties, new Authenticator() {
             
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail,password) ; 
                }
                
            }) ; 
            Message message = prepareMessage(session,myAccountEmail,recepient) ; 
            Transport.send(message) ; 
            System.out.println("message sent");
    
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
      try {
        Message message = new MimeMessage(session) ; 
      message.setFrom(new InternetAddress(myAccountEmail)) ; 
      message.setRecipient(Message.RecipientType.TO , new InternetAddress(recepient)) ; 
      message.setSubject("New Comment added");
      message.setText("hey there ; \n check the new comments ");
      return message ;
      }
      catch ( Exception ex) {
          
      }
      return null ; 
      }
}
