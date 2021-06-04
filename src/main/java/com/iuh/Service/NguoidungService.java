package com.iuh.Service;

import com.iuh.DTO.UserDTO;
import com.iuh.Entity.Khachhang;
import com.iuh.Entity.Nguoiban;
import com.iuh.Entity.Nguoidung;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface NguoidungService {
    Nguoiban registerNguoiban(UserDTO userDTO);
    Khachhang registerKhachhang(UserDTO userDTO);

    Nguoidung getNguoidungbyEmail(String email);



}
