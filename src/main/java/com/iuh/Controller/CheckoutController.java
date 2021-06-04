package com.iuh.Controller;


import com.iuh.Entity.*;
import com.iuh.Repository.*;
import com.iuh.SendEmail.MailService;
import com.iuh.Service.NguoidungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class CheckoutController {
    @Autowired
    SanphamRepository sanphamRepository;

    @Autowired
    DanhmucRepository danhmucRepository;

    @Autowired
    KhachhangRepository khachhangRepository;

    @Autowired
    MailService mailService;

    @Autowired
    NguoibanRepository nguoibanRepository;


    @Autowired
    NguoidungRepository nguoidungRepository;


    @Autowired
    ThuonghieuRepository thuonghieuRepository;


    @Autowired
    NguoidungService nguoidungService;

    @Autowired
    HoadonRepository hoadonRepository;


    @Autowired
    ChitiethoadonRepository chitiethoadonRepository;


    @GetMapping(value="/thanhtoantienmat")
    public String thanhtoantienmat(Model model, HttpSession session, Principal principal
            , @Param("tenkh") String tenkh, @Param("email") String email,
                                   @Param("tensp") String tensp, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                   @RequestParam(name = "size", required = false, defaultValue = "9") Integer size){

            List<Item> cart= (List<Item>) session.getAttribute("cart");

            if(cart==null){
                if(tensp==null){
                    Pageable pageable = PageRequest.of(page, size);
                    Page<Sanpham> sppage = sanphamRepository.findAll(pageable);
                    model.addAttribute("sanphams", sppage);

                    model.addAttribute("danhmucs", danhmucRepository.findAll());

                    model.addAttribute("pagenumber",sppage.getPageable().getPageNumber());
                    int totalpage = sppage.getTotalPages();
                    if(totalpage==0){
                        totalpage =1;
                        model.addAttribute("totalpage",totalpage);
                    }else{
                        model.addAttribute("totalpage",totalpage);
                    }

                    return "trangchu";
                }else {

                    Pageable pageable = PageRequest.of(page, size);
                    Page<Sanpham> sanphams = sanphamRepository.timsanphambyten(tensp,pageable,"true");
                    model.addAttribute("sanphams", sanphams);
                    model.addAttribute("danhmucs", danhmucRepository.findAll());


                    model.addAttribute("pagenumber",sanphams.getPageable().getPageNumber());
                    int totalpage = sanphams.getTotalPages();
                    if(totalpage==0){
                        totalpage =1;
                        model.addAttribute("totalpage",totalpage);
                    }else{
                        model.addAttribute("totalpage",totalpage);
                    }
                    return "trangchu";
                }

            }else{

                session.setAttribute("cart",cart);
                model.addAttribute("tien",tongtiengiohang(cart));

            }
            model.addAttribute("username",principal.getName());
            model.addAttribute("tenkh",findkhachhangbyusername(principal.getName()).getTenkhachhang());
            model.addAttribute("email",findnguoidungbyusername(principal.getName()).getEmail());
            return "thanhtoantienmat";
    }


    @RequestMapping(value = "/dathangtienmat",method = RequestMethod.GET)
    public String dathang(Model model, HttpServletRequest request, HttpSession session, Principal principal,
                          @Param("diachi") String diachi, @Param("ghichu") String ghichu){
        if(principal == null){
            model.addAttribute("username", "");
        }else {
            String name = principal.getName();
            model.addAttribute("username", name);
        }

        Hoadon hd=new Hoadon();
        hd.setKhachhang(findkhachhangbyusername(principal.getName()));
        hd.setNgaylaphd(LocalDate.now());
        hd.setDiachi(diachi);
        hd.setGhichu(ghichu);
        hd.setPhuongthucthanhtoan("Tiền mặt");
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





        return "thanhtoanthanhcong";

    }




    @GetMapping(value="/thanhtoanpaypal")
    public String thanhtoanpaypal(Model model, HttpSession session, Principal principal
            , @Param("tenkh") String tenkh, @Param("email") String email,
                                  @Param("tensp") String tensp, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(name = "size", required = false, defaultValue = "9") Integer size){
        List<Item> cart= (List<Item>) session.getAttribute("cart");

        if(cart==null){

            if(tensp==null){
                Pageable pageable = PageRequest.of(page, size);
                Page<Sanpham> sppage = sanphamRepository.findAll(pageable);
                model.addAttribute("sanphams", sppage);

                model.addAttribute("danhmucs", danhmucRepository.findAll());

                model.addAttribute("pagenumber",sppage.getPageable().getPageNumber());
                int totalpage = sppage.getTotalPages();
                if(totalpage==0){
                    totalpage =1;
                    model.addAttribute("totalpage",totalpage);
                }else{
                    model.addAttribute("totalpage",totalpage);
                }
                return "trangchu";
            }else {

                Pageable pageable = PageRequest.of(page, size);
                Page<Sanpham> sanphams = sanphamRepository.timsanphambyten(tensp,pageable,"true");
                model.addAttribute("sanphams", sanphams);
                model.addAttribute("danhmucs", danhmucRepository.findAll());

                model.addAttribute("pagenumber",sanphams.getPageable().getPageNumber());
                int totalpage = sanphams.getTotalPages();
                if(totalpage==0){
                    totalpage =1;
                    model.addAttribute("totalpage",totalpage);
                }else{
                    model.addAttribute("totalpage",totalpage);
                }
                return "trangchu";
            }

        }else{

            session.setAttribute("cart",cart);
            model.addAttribute("tien",tongtiengiohang(cart));

        }
        model.addAttribute("username",principal.getName());
        model.addAttribute("tenkh",findkhachhangbyusername(principal.getName()).getTenkhachhang());
        model.addAttribute("email",findnguoidungbyusername(principal.getName()).getEmail());

        return "thanhtoanpaypal";
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
