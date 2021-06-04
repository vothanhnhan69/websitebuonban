package com.iuh.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {


    static String AUTH_NGUOIBAN = "NGUOIBAN";
    static String AUTH_KHACHHANG = "KHACHHANG";


    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Sét đặt dịch vụ để tìm kiếm User trong Database.
        // Và sét đặt PasswordEncoder.
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/nguoiban/**").hasRole(AUTH_NGUOIBAN);
        http.authorizeRequests().antMatchers("/nguoiban/quanlysanpham").hasRole(AUTH_NGUOIBAN);
        http.authorizeRequests().antMatchers("/thanhtoan","/danhsachdanhgia","/doimatkhau","/taikhoan","/yeuthich/**","/donhang/**").hasRole(AUTH_KHACHHANG);



        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/");

        http.authorizeRequests().antMatchers(HttpMethod.POST,"/admin/**").anonymous().and().csrf().disable();
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/admin/**").anonymous().and().csrf().disable();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/admin/**").anonymous().and().csrf().disable();



        http
                .authorizeRequests()
                .antMatchers( "/confirm-account","/huydonhang/*","/chitiethoadon/*","/dieukhoan","/getimage/*","/dangnhapfail","/timspbygia","/dangnhap","/login-google","/admin/**","/","/chitietsanpham/*", "/cuahang/sanphamtheoloaidanhmuc/*","/user","/static/**","/giohang/**","/chitietsanpham","/dangkythanhcong","/cuahang","/dangkynguoiban","/luachondangky","/dangkynguoimua").permitAll()// Cho phép tất cả mọi người truy cập vào địa chỉ này
                .anyRequest()
                .authenticated();


        http.authorizeRequests().and().formLogin()//
                .loginProcessingUrl("/j_spring_security_login")//
                .loginPage("/dangnhap")//
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.sendRedirect("/");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        response.sendRedirect("/dangnhapfail");
                    }
                })
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutUrl("/j_spring_security_logout");
    }
}
