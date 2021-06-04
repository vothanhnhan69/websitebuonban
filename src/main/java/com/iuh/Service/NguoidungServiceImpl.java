package com.iuh.Service;

import com.iuh.DTO.UserDTO;
import com.iuh.Entity.Khachhang;
import com.iuh.Entity.Nguoiban;
import com.iuh.Entity.Nguoidung;
import com.iuh.Entity.Vaitro;
import com.iuh.Repository.KhachhangRepository;
import com.iuh.Repository.NguoibanRepository;
import com.iuh.Repository.NguoidungRepository;
import com.iuh.Repository.VaitroRepository;
import com.iuh.Security.Userservice;
import com.iuh.SendEmail.MailService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class NguoidungServiceImpl implements NguoidungService{

    @Autowired
    Userservice userservice;

    @Autowired
    private VaitroRepository vaitroRepository;

    @Autowired
    private NguoidungRepository nguoidungRepository;

    @Autowired
    private KhachhangRepository khachhangRepository;


    @Autowired
    private NguoibanRepository nguoibanRepository;

    @Autowired
    private EmailSenderService emailSenderService;


    @Override
    public Nguoiban registerNguoiban(UserDTO userDTO) {
        Nguoidung users= getUser(userDTO);
        Set<Vaitro> vaitro=new HashSet<>();
        vaitro.add(getvaitro("Nguoiban"));
        users.setVaitros(vaitro);
        users.setEmail(userDTO.getEmail());
        //luu user
        nguoidungRepository.save(users);
        //lay gia tri cho nguoi ban
        Nguoiban nguoiban=new Nguoiban();
        nguoiban.setTennguoiban(userDTO.getUsername());
        nguoiban.setSdt(userDTO.getSdt());
//        nguoiban.setDiachi(userDTO.getDiachi());

        //set thong tin cho nguoi ban
        nguoiban.setNguoidung(users);
        //luu vao csdl
        nguoiban = nguoibanRepository.save(nguoiban);

        return nguoiban;
    }

    @Override
    public Khachhang registerKhachhang(UserDTO userDTO) {
        Nguoidung users= getUser(userDTO);
        Set<Vaitro> vaitro=new HashSet<>();
        vaitro.add(getvaitro("Khachhang"));
        users.setVaitros(vaitro);
        users.setEmail(userDTO.getEmail());
        users.setTrangthai(true);

        nguoidungRepository.save(users);
        //lay gia tri cho nguoi ban

        Khachhang kh=new Khachhang();
        kh.setTenkhachhang(userDTO.getTen());
        kh.setSdt(userDTO.getSdt());
//        kh.setDiachi(null);
        kh.setNguoidung(users);
        kh = khachhangRepository.save(kh);
        return kh;
    }


    @Override
    public Nguoidung getNguoidungbyEmail(String email) {
        Optional<Nguoidung> optional = nguoidungRepository.findByEmail(email);
        if(!optional.isPresent())
            return null;
        return optional.get();
    }



    public Nguoidung getUser(UserDTO userDTO){
        Nguoidung user=new Nguoidung();
        user.setTaikhoan(userDTO.getUsername());
        user.setMatkhau(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setTrangthai(false);

        String randomcode= RandomString.make(64);
        user.setMaxacthuc(randomcode);

        userservice.registerNewUserAccount(user);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userDTO.getEmail());
        mailMessage.setSubject("Hoàn Thành Đăng Ký!");
        mailMessage.setFrom("examplebestcv@gmail.com");
        mailMessage.setText("Để có thể xác thực tài khoản, vui lòng nhấn vào link sau đây : "
                +"http://localhost:8080/confirm-account?token="+user.getMaxacthuc());
        emailSenderService.sendEmail(mailMessage);
        return user;
    }



    public Vaitro getvaitro(String name){
        return vaitroRepository.findByten(name);
    }



}
