package service;

import java.time.LocalTime;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
    private String sender;
    private String pass;

    public Mail(String sender, String pass) {
        this.sender = sender;
        this.pass = pass;
    }

    public void sendEmail(String reciever, String title, String messageText) {
        assert reciever != null && title != null && messageText != null;

        // using google host
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        try {
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.user", sender);
            properties.put("mail.smtp.password", pass);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
        } catch (Exception e) {
            System.out.print(e.getLocalizedMessage());
        }
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(sender));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciever));

            // Set Subject: header field
            message.setSubject(title);

            // Now set the actual message
            message.setText(messageText);

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.connect(host, sender, pass);
            transport.sendMessage(message, message.getAllRecipients());

            System.out.println(
                    "Message from: " + sender + " to " + reciever + " sent successfully at: " + LocalTime.now());
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void sendMail(String email, String title, String message) {
        Mail mail = new Mail("danishCrownProject@gmail.com", "danishcrown1");
        mail.sendEmail(email, title, message);
    }

}
