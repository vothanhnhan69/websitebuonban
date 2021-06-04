package com.iuh.SendEmail;

import com.iuh.Entity.Hoadon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class MailService {
    private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${spring.mail.username}")
    private String email;
    @Value("${spring.mail.password}")
    private String password;


    @Autowired
    ThymeleafService thymeleafService;


    public void sendMail(String emainhan, String tenkh, Hoadon hoadon,double tongtien) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        Message message = new MimeMessage(session);
        try {

            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(emainhan)});

            message.setFrom(new InternetAddress(email));
            message.setSubject("Đơn hàng MCK shop");
            message.setContent(thymeleafService.getContent(tenkh,hoadon,tongtien), CONTENT_TYPE_TEXT_HTML);

            System.out.println("Gửi email thành công");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }





}
