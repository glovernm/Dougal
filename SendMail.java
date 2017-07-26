// File Name SendEmail.java

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {

   public static void main(String [] args) {    
      // Recipient's email ID needs to be mentioned.
      String to = "gleasowa@plu.edu";

      // Sender's email ID needs to be mentioned
      final String from = "microsys@plu.edu";
      final String password = "M1crosy$";

      // Gmail Host for sending email
      String host = "smtp.gmail.com";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.host", host);
      properties.setProperty("mail.smtp.port", "587");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable","true");
      
      //Authentication for Gmail Login
      Authenticator auth = new Authenticator() {
    	  protected PasswordAuthentication getPasswordAuthentication() {
    		  return new PasswordAuthentication(from, password);
    	  }
      };
      
      // Creates Session instance using properties and authentication.
      Session session = Session.getInstance(properties, auth);

      try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("Test Sending Email");

         // Now set the actual message
         message.setText("Test Message from microsys@plu.edu");

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
         mex.getCause();
      }
   }
}