// File Name SendEmail.java

import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {
	
	private String to;
	private String subject;
	private String emailMessage;
	private String filename;
	private final String from = "microsys@plu.edu";
	private final String password = "M1crosy$";
	private boolean attachment;

   public SendMail(String to,String subject, String message) {    
      // Recipient's email ID needs to be mentioned.
      this.to = to;
      this.subject = subject;
      this.emailMessage = message;
      attachment = false;
   }
   
   public SendMail(String to,String subject, String message, String filename) {
	      this.to = to;
	      this.subject = subject;
	      this.emailMessage = message;
	      this.filename = filename;
	      attachment = true;
   }

   public String send() {
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
         message.setSubject(this.subject);
         
         // Create the message part 
         if(attachment) {
         BodyPart messageBodyPart = new MimeBodyPart();

         // Fill the message
         messageBodyPart.setText(emailMessage);
         
        
         // Create a multipar message
         Multipart multipart = new MimeMultipart();

         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         // Part two is attachment
         messageBodyPart = new MimeBodyPart();
         DataSource source = new FileDataSource(filename);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(filename);
         multipart.addBodyPart(messageBodyPart);

         // Send the complete message parts
         message.setContent(multipart);
         }
         
         else {
        	 message.setText(emailMessage);
         }

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
         mex.getCause();
      }    
      
      return "Sent Successfully";
   }

		public String getTo() {
			return to;
		}
		
		public void setTo(String to) {
			this.to = to;
		}
		
		public String getSubject() {
			return subject;
		}
		
		public void setSubject(String subject) {
			this.subject = subject;
		}
		
		public String getMessage() {
			return emailMessage;
		}
		
		public void setMessage(String message) {
			this.emailMessage = message;
		}
		
		public String getFileName() {
			return filename;
		}
		
		public void setFileName(String filename) {
			this.filename = filename;
		}
		
		public void setParams(String to, String subject, String message) {
			this.to = to;
			this.subject = subject;
			this.emailMessage = message;
		}
		
		public void setParams(String to, String subject, String message, String filename) {
			this.to = to;
			this.subject = subject;
			this.emailMessage = message;
			this.filename = filename;
		}
}