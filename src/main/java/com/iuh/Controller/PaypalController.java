package com.iuh.Controller;

import com.iuh.Entity.*;
import com.iuh.Paypal.PaypalPaymentIntent;
import com.iuh.Paypal.PaypalPaymentMethod;
import com.iuh.Paypal.PaypalService;
import com.iuh.Paypal.Utils;
import com.iuh.Repository.*;
import com.iuh.SendEmail.MailService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PaypalController {
    public static final String URL_PAYPAL_SUCCESS = "pay/paypalsuccess";
    public static final String URL_PAYPAL_CANCEL = "pay/paypalcancel";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    HoadonRepository hoadonRepository;

    @Autowired
    ChitiethoadonRepository chitiethoadonRepository;

    @Autowired
    KhachhangRepository khachhangRepository;

    @Autowired
    SanphamRepository sanphamRepository;

    @Autowired
    MailService mailService;

    @Autowired
    private PaypalService paypalService;


    @Autowired
    NguoidungRepository nguoidungRepository;



    @PostMapping("/pay")
    public String pay(HttpServletRequest request, @RequestParam("tien") double price, Model model){
        String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }


        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }



    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay(){
        return "paypalcancel";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId,
                             Model model, HttpServletRequest request, HttpSession session,
                             Principal principal,@RequestParam("diachi") String diachi, @RequestParam("ghichu") String ghichu){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                Hoadon hd=new Hoadon();
                hd.setKhachhang(findkhachhangbyusername(principal.getName()));
                hd.setNgaylaphd(LocalDate.now());

                hd.setDiachi(diachi);
                hd.setGhichu(ghichu);

                hd.setPhuongthucthanhtoan("Paypal");


                hoadonRepository.save(hd);
                ArrayList<Chitiethoadon> listcthd=new ArrayList<>();
                List<Item> cart= (List<Item>) session.getAttribute("cart");
                for (Item item: cart){
                    Chitiethoadon chitiethoadon=new Chitiethoadon();
                    chitiethoadon.setHoadon(hoadonRepository.findById((long) hoadonRepository.findAll().size()).get());
                    chitiethoadon.setSanpham(item.getSanpham());
                    chitiethoadon.setSoluong(item.getSoluong());
                    chitiethoadon.setTrangthai("Đang chờ xác nhận");
                    chitiethoadon.setDongia(item.getSanpham().getDongia()-item.getSanpham().getDongia()*item.getSanpham().getKhuyenmai()/100);

                    //update so luong san pham khi ban hang
                    Sanpham sp= sanphamRepository.findById(item.getSanpham().getMasanpham()).get();
                    sp.setSoluong(sp.getSoluong()-item.getSoluong());
                    sanphamRepository.save(sp);
                    listcthd.add(chitiethoadon);
                    chitiethoadonRepository.save(chitiethoadon);
                }



                hd.setListcthd(listcthd);
                //send email
                mailService.sendMail(findnguoidungbyusername(principal.getName()).getEmail(),findkhachhangbyusername(principal.getName()).getTenkhachhang(),hd,tongtiengiohang(cart));
                session.removeAttribute("cart");
                return "paypalsuccess";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }


    public Nguoidung findnguoidungbyusername(String username){
        Nguoidung khachhang = nguoidungRepository.findBytaikhoan(username);
        return khachhang;
    }


    public double tongtiengiohang(List<Item> cart){
        double tongtien=0;
        for(int i=0;i<cart.size();i++){
            tongtien+=cart.get(i).getSoluong()*(cart.get(i).getSanpham().getDongia()-cart.get(i).getSanpham().getDongia()*cart.get(i).getSanpham().getKhuyenmai()/100);
        }
        return tongtien;
    }

    public Khachhang findkhachhangbyusername(String username){
        Khachhang khachhang = khachhangRepository.findKhachhangByUsername(username);
        return khachhang;
    }

}
