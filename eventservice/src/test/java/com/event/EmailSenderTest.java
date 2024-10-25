package com.event;

import com.event.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    private EmailService emailService;

    @Test
    void emailSendTest(){
        System.out.println("sending email....");
        emailService.sendEmail("sakshijain0922@gmail.com","Email from spring boot","This email is send using while creating email service");
    }
    @Test
    void sendHtmlInEmail(){
        String html = "" +
                "<h1 style='color:red;border:1px solid red;'> Welcome to learn code with durgesh </h1>" + "";
        emailService.sendEmailwithHtml("sakshijain0922@gmail.com","Email from spring boot",html);
    }
}
