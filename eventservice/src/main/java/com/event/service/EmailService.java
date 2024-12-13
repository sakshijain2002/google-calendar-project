package com.event.service;

import com.event.entity.Event;
import com.event.entity.Guest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Set;

@Service
public class EmailService {

    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;



    public void sendEmail(String to ,String subject,String message){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        simpleMailMessage.setFrom("sakshijain2090@gmail.com");
        try {
            mailSender.send(simpleMailMessage);  // Sends the email
            logger.info("Email sent successfully to {}", to);
        } catch (Exception e) {
            logger.error("Error sending email to {}: {}", to, e.getMessage());
        }
    }
    public void sendEmailToGuests(Set<Guest> guests, String subject, String message) {
        // Extract emails from the guest set
        String[] guestEmails = guests.stream()
                .map(Guest::getEmail)
                .toArray(String[]::new);

        // Send the email to all guests
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(guestEmails); // Set recipients
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailMessage.setFrom("sakshijain2090@gmail.com");

            mailSender.send(mailMessage);
            logger.info("Emails sent successfully to guests");
        } catch (Exception e) {
            logger.error("Error sending emails to guests: {}", e.getMessage());
        }
    }
    public void sendEmail(String[] to,String subject,String message){

    }
    public void sendEmailwithHtml(String to,String subject,String htmlContent){
        MimeMessage simpleMailMessage = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(simpleMailMessage,true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("sakshijain2090@gmail.com");
            helper.setText(htmlContent,true);
            mailSender.send(simpleMailMessage);
            logger.info("Email has been sent");
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendEmailWithHtmlToGuests(Set<Guest> guests, String subject, String htmlContent,String eventCreatorMail) {
        for (Guest guest : guests) {
            String guestEmail = guest.getEmail();
            if (guestEmail == null || !isValidEmail(guestEmail)) {
                logger.warn("Invalid email for guest: {}", guestEmail);
                continue;
            }

            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                helper.setTo(guestEmail);
                helper.setSubject(subject);
                helper.setText(htmlContent, true);
                helper.setFrom(eventCreatorMail);

                mailSender.send(mimeMessage);
                logger.info("Email sent successfully to {}", guestEmail);
            } catch (MessagingException e) {
                logger.error("Error sending email to {}: {}", guestEmail, e.getMessage());
            }
        }
    }

    public JavaMailSender createMailSender(String email, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Configure SMTP server
        mailSender.setHost("smtp.gmail.com");  // Use the correct SMTP server
        mailSender.setPort(587);  // Port for Gmail SMTP
        mailSender.setUsername(email);  // Event creator's email
        mailSender.setPassword(password);  // Event creator's email password (or App password if 2FA is enabled)

        // Set additional SMTP properties (TLS and SSL)
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return mailSender;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; // Basic email regex
        return email.matches(emailRegex);
    }
}


