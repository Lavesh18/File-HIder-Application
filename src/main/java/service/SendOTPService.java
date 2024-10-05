package service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendOTPService {
    public static void sendOTP(String email,String genOTP)
    {
        String toUserEmail = email;
        String Subject = "File Hider OTP!";
        String messageText = "Your OTP for the File Hider is ";

        String fromAdminEmail = "YOUR_EMAIL";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "PORT");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties,new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(fromAdminEmail, "YOUR_EMAIL_PASSWORD");
            }
        });

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromAdminEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toUserEmail));

            message.setSubject(Subject);
            message.setText(messageText+genOTP);
            Transport.send(message);
            System.out.println("Mail sent successfully...");
        } catch (MessagingException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}