package com.iuh.Security;

import com.iuh.Entity.Nguoidung;
import com.iuh.Entity.Vaitro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class UserDetail implements UserDetails {
    private Nguoidung nguoidung;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserDetail() {
    }

    public UserDetail(Nguoidung nguoidung) {
        this.nguoidung = nguoidung;
    }

    public Nguoidung getNguoidung() {
        return nguoidung;
    }

    public void setNguoidung(Nguoidung nguoidung) {
        this.nguoidung = nguoidung;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection=new ArrayList<>();
        for (Vaitro vt : nguoidung.getVaitros()){

            if(vt.getTen().equalsIgnoreCase("Khachhang")){
                collection.add(new SimpleGrantedAuthority("ROLE_KHACHHANG"));
            }else if(vt.getTen().equalsIgnoreCase("Admin")){
                collection.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }else if(vt.getTen().equalsIgnoreCase("Nguoiban")){
                collection.add(new SimpleGrantedAuthority("ROLE_NGUOIBAN"));
            }
        }
        return collection;
    }


    @Override
    public String getPassword() {

        return nguoidung.getMatkhau();
    }

    @Override
    public String getUsername() {
        return nguoidung.getTaikhoan();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return nguoidung.isTrangthai();
    }


    @Override
    public String toString() {
        return "UserDetail{" +
                "nguoidung=" + nguoidung +
                '}';
    }
}
