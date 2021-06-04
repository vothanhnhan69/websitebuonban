package com.iuh.Security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        try{
            System.out.println(exception);
            if(exception.getClass() == UsernameNotFoundException.class){
                System.out.println("usernotexits");
                throw new BadCredentialsException("usernotexits");


            }else if(exception.getClass() == BadCredentialsException.class) {
                System.out.println("passwordwrong");
                throw new BadCredentialsException("passwordwrong");


            }
        }catch (Exception e){
            System.out.println(e);
            String redirectUrl = request.getContextPath() + "/dangnhap?error="+e.getMessage();
            response.sendRedirect(redirectUrl);
        }


    }
}
