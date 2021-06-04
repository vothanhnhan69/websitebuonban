package com.iuh.Security;

import com.iuh.Entity.Nguoidung;
import com.iuh.Repository.NguoidungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Userservice implements UserDetailsService {

    @Autowired
    private NguoidungRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws  UsernameNotFoundException{
        Nguoidung user = userRepository.findBytaikhoan(username);

        if (user == null) {
            System.out.println("Not Exist User");
            throw new UsernameNotFoundException("Not Exist User");
        }
        else{

            return new UserDetail(user);
        }
    }



    public Nguoidung registerNewUserAccount(Nguoidung user){
        user.setMatkhau(passwordEncoder.encode(user.getMatkhau()));
        return user;


    }
}
