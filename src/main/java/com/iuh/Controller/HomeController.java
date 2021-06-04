    package com.iuh.Controller;

    import com.iuh.DTO.UserDTO;
    import com.iuh.Entity.*;

    import com.iuh.Repository.*;
    import com.iuh.Service.EmailSenderService;
    import com.iuh.Service.NguoidungService;
    import org.apache.http.client.ClientProtocolException;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.repository.query.Param;
    import org.springframework.mail.SimpleMailMessage;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpSession;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.security.Principal;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;

    @Controller
    @RequestMapping
    public class HomeController {

        @Autowired
        SanphamRepository sanphamRepository;

        @Autowired
        DanhmucRepository danhmucRepository;

        @Autowired
        KhachhangRepository khachhangRepository;


        @Autowired
        NguoibanRepository nguoibanRepository;


        @Autowired
        NguoidungRepository nguoidungRepository;

        @Autowired
        DiachiRepository diachiRepository;

        @Autowired
        ThuonghieuRepository thuonghieuRepository;


        @Autowired
        NguoidungService nguoidungService;

        @Autowired
        HoadonRepository hoadonRepository;


        @Autowired
        ChitiethoadonRepository chitiethoadonRepository;

        @Autowired
        YeuthichRepository yeuthichRepository;

        @Autowired
        DanhgiaRepository danhgiaRepository;

        @Autowired
        PasswordEncoder passwordEncoder;

        @Autowired
        private EmailSenderService emailSenderService;


        @GetMapping(value = "/hoadon")
        public String hoadon(Model model){
            model.addAttribute("name","hậu");
            return "sendemail";
        }


        @GetMapping(value="/dieukhoan")
        public String dieukhoan(Model model, HttpSession session,Principal principal){
            if(principal == null){
                model.addAttribute("username", "");
            }else {
                String name = principal.getName();
                model.addAttribute("username", name);
            }

            return "dieukhoanchinhsach";
        }



        @RequestMapping(value = "/huydonhang/{machitiethoadon}", method = RequestMethod.GET)
        public String huydonhang(@PathVariable("machitiethoadon") Long machitiethoadon,Model model,Principal principal) {


            Chitiethoadon cthd=chitiethoadonRepository.findById(machitiethoadon).get();
             if(cthd.getTrangthai().equalsIgnoreCase("Đang chờ xác nhận")){

//                 model.addAttribute("username",principal.getName());
//                 model.addAttribute("danhmucs", danhmucRepository.findAll());
                 model.addAttribute("errordonhang","Hủy đơn hàng thành công");
//                 model.addAttribute("chitiethoadons", chitiethoadonRepository.listcthdbyhd(cthd.getHoadon().getMahd()));
                 cthd.setTrangthai("Hủy đơn hàng");
                 chitiethoadonRepository.save(cthd);

                 return "redirect:/chitiethoadon/"+cthd.getHoadon().getMahd();
             }else {

//                 model.addAttribute("username", principal.getName());
//                 model.addAttribute("danhmucs", danhmucRepository.findAll());
                 model.addAttribute("errordonhang", "Không thể hủy vì đơn đã được xét duyệt");
//                 model.addAttribute("chitiethoadons", chitiethoadonRepository.listcthdbyhd(cthd.getHoadon().getMahd()));
                 return "redirect:/chitiethoadon/"+cthd.getHoadon().getMahd();
             }
        }


        @RequestMapping(value = "capnhatkh", method = RequestMethod.POST)
        public String themsanpham(Khachhang khachhang,Model model,Principal principal) {
            khachhangRepository.save(khachhang);
            model.addAttribute("error1","Cập nhật thành công");
            Khachhang kh = findkhachhangbyusername(principal.getName());
            model.addAttribute("username",principal.getName());
            model.addAttribute("danhmucs", danhmucRepository.findAll());
            model.addAttribute("diachi",diachiRepository.finddiachibynguoidung(kh.getNguoidung().getMa()));
            model.addAttribute("khachhang", kh);
            model.addAttribute("");
            return "taikhoan";
        }



        @GetMapping(value="/")
        public String home(Model model,@Param("tensp") String tensp, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                           @RequestParam(name = "size", required = false, defaultValue = "9") Integer size,Principal principal){
            if(principal == null){
                model.addAttribute("username", "");
            }else {
                String name = principal.getName();
                model.addAttribute("username", name);
            }

            if(tensp==null){
                Pageable pageable = PageRequest.of(page, size);
                Page<Sanpham> sppage = sanphamRepository.timspbytrangthang("true",pageable);
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
        }


        @RequestMapping(value="/chitietsanpham/{masanpham}", method = RequestMethod.GET)
        public String chitietsanpham(Model model,@PathVariable("masanpham") Long id,Principal principal){
            model.addAttribute("danhmucs", danhmucRepository.findAll());
            Optional<Sanpham> th = sanphamRepository.findById(id);
            th.ifPresent(item -> model.addAttribute("sanpham", th.get()));

            Danhgia dg=new Danhgia();

            dg.setSanpham(th.get());
            model.addAttribute("listdanhgia",danhgiaRepository.listdanhgiabysp(id));

            model.addAttribute("danhgia",dg);
            if(principal==null){
                model.addAttribute("username","");
            }else {
                model.addAttribute("username", principal.getName());
            }
            return "chitietsanpham";
        }



        @RequestMapping(value = "/capnhatdiachi",method = RequestMethod.GET)
        public  String capnhatdiachi(Model model,Principal principal){
            model.addAttribute("danhmucs", danhmucRepository.findAll());
           model.addAttribute("diachi",findnguoidungbyusername(principal.getName()).getDsdiachi().get(0));
            model.addAttribute("username", principal.getName());
            return "capnhatdiachi";
        }


        @RequestMapping(value = "/dangnhapfail",method = RequestMethod.GET)
        public  String dangnhapfail(Model model){
            model.addAttribute("errorfail", "Thông tin đăng nhập không hợp lệ");
            return "dangnhap";
        }


        @RequestMapping(value = "/updatediachi" ,method = RequestMethod.POST)
        public  String updatediachi(Diachi diachi,Model model,Principal principal){
           diachiRepository.save(diachi);
            model.addAttribute("error1","Cập nhật địa chỉ thành công");
            model.addAttribute("danhmucs", danhmucRepository.findAll());
            Khachhang kh = findkhachhangbyusername(principal.getName());
            model.addAttribute("username",principal.getName());
            model.addAttribute("diachi",diachiRepository.finddiachibynguoidung(kh.getNguoidung().getMa()));
            model.addAttribute("khachhang", kh);

            return "taikhoan";
        }


        @RequestMapping(value="/cuahang/sanphamtheoloaidanhmuc/{maloaidanhmuc}",method = RequestMethod.GET)
        public String sanphamtheodanhmuc(Model model,@PathVariable("maloaidanhmuc") Long madanhmuc, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(name = "size", required = false, defaultValue = "9") Integer size,Principal principal){
                Pageable pageable = PageRequest.of(page, size);
                Page<Sanpham> sppage = sanphamRepository.listspbydanhmuc(madanhmuc,pageable);
                model.addAttribute("sanphams", sppage);
                model.addAttribute("danhmucs", danhmucRepository.findAll());
                if(principal==null){
                    model.addAttribute("username", "");
                }else {
                    model.addAttribute("username", principal.getName());
                }
                model.addAttribute("pagenumber",sppage.getPageable().getPageNumber());
                int totalpage = sppage.getTotalPages();
                if(totalpage==0){
                    totalpage =1;
                    model.addAttribute("totalpage",totalpage);
                }else{
                    model.addAttribute("totalpage",totalpage);
                }
                return "sanphamtheoloaidanhmuc";
        }



        @RequestMapping(value="/chitiethoadon/{mahd}",method = RequestMethod.GET)
        public String chitiethoadon(Model model,@PathVariable("mahd") Long mahd,Principal principal){
            model.addAttribute("chitiethoadons", chitiethoadonRepository.listcthdbyhd(mahd));
            model.addAttribute("danhmucs", danhmucRepository.findAll());
            model.addAttribute("mahd",mahd);
                model.addAttribute("username", principal.getName());

            double tongtien=0;
            for (int i=0;i<chitiethoadonRepository.listcthdbyhd(mahd).size();i++){
                tongtien+=chitiethoadonRepository.listcthdbyhd(mahd).get(i).getDongia()*chitiethoadonRepository.listcthdbyhd(mahd).get(i).getSoluong();
            }

            model.addAttribute("tien",tongtien);
            return "chitiethoadon";
        }



        @GetMapping(value="/taikhoan")
        public String taikhoan(Model model,Principal principal){
            if(principal.getName()==null){
                return "dangnhap";
            }else {
                Khachhang kh = findkhachhangbyusername(principal.getName());
                model.addAttribute("username",principal.getName());
                model.addAttribute("danhmucs", danhmucRepository.findAll());
                model.addAttribute("diachi",diachiRepository.finddiachibynguoidung(kh.getNguoidung().getMa()));
                model.addAttribute("khachhang", kh);
                model.addAttribute("error1",null);
                return "taikhoan";
            }

        }


        @GetMapping(value="/doimatkhau")
        public String doimatkhau(Model model,Principal principal){

                Nguoidung nguoidung = findnguoidungbyusername(principal.getName());
                model.addAttribute("username",principal.getName());
                model.addAttribute("danhmucs", danhmucRepository.findAll());
                model.addAttribute("nguoidung", nguoidung);
                System.out.println(nguoidung);
                return "doimatkhau";
        }

        @RequestMapping(value="updatepassword",method = RequestMethod.POST)
        public String doimatkhau(Model model,Nguoidung nguoidung,Principal principal){

            model.addAttribute("username",principal.getName());
            model.addAttribute("danhmucs", danhmucRepository.findAll());
            model.addAttribute("nguoidung", nguoidung);
            model.addAttribute("error1","Đổi mật khẩu thành công");
            Khachhang kh = findkhachhangbyusername(principal.getName());
            model.addAttribute("diachi",diachiRepository.finddiachibynguoidung(kh.getNguoidung().getMa()));
            model.addAttribute("khachhang", kh);

            nguoidung.setVaitros(findnguoidungbyusername(principal.getName()).getVaitros());
            nguoidung.setMatkhau(passwordEncoder.encode(nguoidung.getMatkhau()));
            nguoidungRepository.save(nguoidung);

            return "taikhoan";
        }




        @GetMapping(value="/cuahang")
        public String cuahang(Model model,@Param("tensp") String tensp, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(name = "size", required = false, defaultValue = "15") Integer size,Principal principal){

            if(tensp==null){
                if(principal==null){
                    model.addAttribute("username", "");
                }else {
                    model.addAttribute("username", principal.getName());
                }
                Pageable pageable = PageRequest.of(page, size);
                Page<Sanpham> sppage = sanphamRepository.timspbytrangthang("true",pageable);
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

                return "cuahang";
            }else {
                if(principal==null){
                    model.addAttribute("username", "");
                }else {
                    model.addAttribute("username", principal.getName());
                }
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
                return "cuahang";
            }
        }


        @GetMapping(value={"/dangnhap"})
        public String dangnhap(Model model){
            model.addAttribute("error",null);
            model.addAttribute("errorfail",null);
            return "dangnhap";
        }

        @GetMapping(value="/luachondangky")
        public String dangky(){
            return "luachondangky";
        }





        @GetMapping(value="/dangkynguoimua")
        public String dangkynguoimua(Model model){
            model.addAttribute("userdto",new UserDTO());

            return "dangkynguoimua";
        }


        @GetMapping(value="/dangkynguoiban")
        public String dangkynguoiban(Model model){
            model.addAttribute("userdto",new UserDTO());

            return "dangkynguoiban";
        }


        @GetMapping(value = "/donhang")
        public String donhang(Model model,Principal principal,
                              @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
            if(principal == null){
                model.addAttribute("username","");
                return "dangnhap";
            }else {
                String name = principal.getName();
                model.addAttribute("username", name);
                model.addAttribute("danhmucs", danhmucRepository.findAll());
                model.addAttribute("errordonhang","");
                Pageable pageable = PageRequest.of(page, size);
                Page<Hoadon> hdpage = hoadonRepository.listhdbykh(name,pageable);
                model.addAttribute("hoadons", hdpage);
                model.addAttribute("pagenumber",hdpage.getPageable().getPageNumber());
                int totalpage = hdpage.getTotalPages();
                if(totalpage==0){
                    totalpage =1;
                    model.addAttribute("totalpage",totalpage);
                }else{
                    model.addAttribute("totalpage",totalpage);
                }
                return "donhang";
            }
        }


        @GetMapping(value = "/yeuthich")
        public String yeuthich(Model model,Principal principal,
                              @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                              @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
            if(principal == null){
                model.addAttribute("username","");
                return "dangnhap";
            }else {

                String name = principal.getName();
                model.addAttribute("username", name);
                model.addAttribute("danhmucs", danhmucRepository.findAll());
                model.addAttribute("erroryeuthich", null);

                Pageable pageable = PageRequest.of(page, size);
                Page<Yeuthich> pageyeuthich = yeuthichRepository.listyeuthichbykh(name,pageable);
                int totalpage = pageyeuthich.getTotalPages();
                if(totalpage==0){
                    totalpage =1;
                    model.addAttribute("totalpage",totalpage);
                }else{
                    model.addAttribute("totalpage",totalpage);
                }
                model.addAttribute("yeuthichs",pageyeuthich);
                return "yeuthich";
            }
        }

        @GetMapping(value = "/danhsachdanhgia")
        public String danhsachdanhgia(Model model,Principal principal,
                               @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
            if(principal == null){
                model.addAttribute("username","");
                return "danhsachdanhgia";
            }else {

                String name = principal.getName();
                model.addAttribute("username", name);
                model.addAttribute("danhmucs", danhmucRepository.findAll());
                model.addAttribute("errordanhgia", null);

                Pageable pageable = PageRequest.of(page, size);


                Page<Danhgia> pagedanhgia = danhgiaRepository.listdanhgiabykh(findkhachhangbyusername(name).getMakhachhang(),pageable);


                int totalpage = pagedanhgia.getTotalPages();
                if(totalpage==0){
                    totalpage =1;
                    model.addAttribute("totalpage",totalpage);
                }else{
                    model.addAttribute("totalpage",totalpage);
                }
                model.addAttribute("danhgias",pagedanhgia);
                return "danhsachdanhgia";
            }
        }



        @RequestMapping(value = "/danhsachdanhgia/xoa/{masanpham}",method = RequestMethod.GET)
        public String xoadanhgia(Model model,@PathVariable(value = "masanpham") Long masanpham,
                                  Principal principal,  @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
            Pageable pageable = PageRequest.of(page, size);
            Page<Danhgia> list= danhgiaRepository.listdanhgiabykh(findkhachhangbyusername(principal.getName()).getMakhachhang(),pageable);

            danhgiaRepository.delete(danhgiaRepository.findDanhgiaBymasanpham(masanpham,findkhachhangbyusername(principal.getName()).getMakhachhang()));

            String name = principal.getName();

            Page<Danhgia> pageyeuthich = danhgiaRepository.listdanhgiabykh(findkhachhangbyusername(principal.getName()).getMakhachhang(), pageable);

            int totalpage = pageyeuthich.getTotalPages();
            if (totalpage == 0) {
                totalpage = 1;
                model.addAttribute("totalpage", totalpage);
            } else {
                model.addAttribute("totalpage", totalpage);
            }
            model.addAttribute("username", principal.getName());
            model.addAttribute("errordanhgia", "Xóa đánh giá thành công");
            model.addAttribute("danhgias", pageyeuthich);
            model.addAttribute("danhmucs", danhmucRepository.findAll());
            return "danhsachdanhgia";
        }


        //them yeu thich
        @RequestMapping(value = "/yeuthich/{masanpham}",method = RequestMethod.GET)
        public String themyeuthich(UserDTO userDTO, Model model,@PathVariable(value = "masanpham") Long masanpham,
                                   Principal principal,  @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                   @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
            if(principal == null){
                model.addAttribute("username","");
                return "dangnhap";
            }else {
                List<Yeuthich> list= yeuthichRepository.listyeuthich(principal.getName());
                int index= isExitsyeuthich(masanpham,list);

                if(index==-1){
                    Yeuthich yeuthich = new Yeuthich();
                    yeuthich.setSanpham(sanphamRepository.findById(masanpham).get());
                    yeuthich.setKhachhang(findkhachhangbyusername(principal.getName()));
                    yeuthichRepository.save(yeuthich);
                    String name = principal.getName();
                    Pageable pageable = PageRequest.of(page, size);
                    Page<Yeuthich> pageyeuthich = yeuthichRepository.listyeuthichbykh(name, pageable);
                    int totalpage = pageyeuthich.getTotalPages();
                    if (totalpage == 0) {
                        totalpage = 1;
                        model.addAttribute("totalpage", totalpage);
                    } else {
                        model.addAttribute("totalpage", totalpage);
                    }
                    model.addAttribute("yeuthichs", pageyeuthich);
                    model.addAttribute("danhmucs", danhmucRepository.findAll());
                    return "redirect:/yeuthich";
                }else {
                    String name = principal.getName();
                    Pageable pageable = PageRequest.of(page, size);
                    Page<Yeuthich> pageyeuthich = yeuthichRepository.listyeuthichbykh(name, pageable);
                    int totalpage = pageyeuthich.getTotalPages();
                    if (totalpage == 0) {
                        totalpage = 1;
                        model.addAttribute("totalpage", totalpage);
                    } else {
                        model.addAttribute("totalpage", totalpage);
                    }
                    model.addAttribute("yeuthichs", pageyeuthich);
                    model.addAttribute("danhmucs", danhmucRepository.findAll());
                    return "redirect:/yeuthich";
                }
            }
        }



        @RequestMapping(value = "/yeuthich/xoa/{masanpham}",method = RequestMethod.GET)
        public String xoayeuthich(Model model,@PathVariable(value = "masanpham") Long masanpham,
                                  Principal principal,  @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(name = "size", required = false, defaultValue = "10") Integer size){
            List<Yeuthich> list= yeuthichRepository.listyeuthich(principal.getName());
            yeuthichRepository.delete(yeuthichRepository.findYeuthichBymasanpham(masanpham,findkhachhangbyusername(principal.getName()).getMakhachhang()));
            String name = principal.getName();
            Pageable pageable = PageRequest.of(page, size);
            Page<Yeuthich> pageyeuthich = yeuthichRepository.listyeuthichbykh(name, pageable);

            int totalpage = pageyeuthich.getTotalPages();
            if (totalpage == 0) {
                totalpage = 1;
                model.addAttribute("totalpage", totalpage);
            } else {
                model.addAttribute("totalpage", totalpage);
            }

            model.addAttribute("yeuthichs", pageyeuthich);
            model.addAttribute("danhmucs", danhmucRepository.findAll());
            return "redirect:/yeuthich";
        }



        @RequestMapping(value = "dangkynguoimua", method = RequestMethod.POST)
        public String dangkynguoimua(UserDTO userDTO, Model model,HttpServletRequest httpRequest) {
            if(notexistEmailandUsername(userDTO.getEmail(),userDTO.getUsername())==true){
                Khachhang kh=nguoidungService.registerKhachhang(userDTO);
                Diachi dc=new Diachi();
                dc.setNguoidung(kh.getNguoidung());
                diachiRepository.save(dc);
                model.addAttribute("error","Đăng ký thành công");
                return "dangnhap";
            }else {
                UserDTO userDTO1 = new UserDTO();
                userDTO1.setTen(userDTO.getTen());
                userDTO1.setEmail(userDTO.getEmail());
                userDTO1.setSdt(userDTO.getSdt());
                model.addAttribute("error", "Tên tài khoản hoặc email đã tồn tại");
                model.addAttribute("userdto", userDTO1);
                return "dangkynguoimua";
            }

        }


        //kiểm tra có trùng email và username hay không
        public boolean notexistEmailandUsername(String email,String username){
            if(nguoidungRepository.findBytaikhoan(username)!=null){
                return false;
            }
            else if(nguoidungService.getNguoidungbyEmail(email)!=null){
                return false;
            }
            return true;
        }



        @RequestMapping(value = "dangkynguoiban", method = RequestMethod.POST)
        public String dangkynguoiban(UserDTO userDTO, Model model) {
                if(notexistEmailandUsername(userDTO.getEmail(),userDTO.getUsername())==true){
                    Nguoiban nguoiban= nguoidungService.registerNguoiban(userDTO);
                    Diachi dc=new Diachi();
                    dc.setNguoidung(nguoiban.getNguoidung());
                    diachiRepository.save(dc);
                    model.addAttribute("error","Đăng ký thành công, xác thực email để có thể đăng nhập");
                    return "dangnhap";
                }else {
                    UserDTO userDTO1 = new UserDTO();
                    userDTO1.setTen(userDTO.getTen());
                    userDTO1.setEmail(userDTO.getEmail());
                    userDTO1.setSdt(userDTO.getSdt());
                    model.addAttribute("error", "Tên tài khoản hoặc email đã tồn tại");
                    model.addAttribute("userdto", userDTO1);
                    return "dangkynguoiban";
                }
        }


        public boolean kiemtrausername(String username){
            if(nguoidungRepository.findBytaikhoan(username)!=null){
                 return false;
            } else return true;
        }


        @GetMapping(value="/giohang")
        public String giohang(Model model, HttpSession session,Principal principal){

            if(principal == null){
                model.addAttribute("username", "");
            }else {
                String name = principal.getName();
                model.addAttribute("username", name);
            }
                List<Item> cart= (List<Item>) session.getAttribute("cart");
                if(cart==null){
                    session.setAttribute("cart",cart);
                    model.addAttribute("tien",0);
                }else{
                    session.setAttribute("cart",cart);
                    model.addAttribute("tien",tongtiengiohang(cart));
                }
                return "giohang";
        }




        @RequestMapping(value = "/giohang/{masanpham}",method = RequestMethod.GET)
        public String themvaogiohang(Model model,@PathVariable(value = "masanpham") Long masanpham, HttpSession session,Principal principal){

            if(principal == null){
                model.addAttribute("username", "");
            }else {
                String name = principal.getName();
                model.addAttribute("username", name);
            }


            if(session.getAttribute("cart")==null){
                List<Item> cart=new ArrayList<Item>();
                cart.add(new Item(sanphamRepository.findById(masanpham).get(),1));
                session.setAttribute("cart",cart);
                model.addAttribute("tien",tongtiengiohang(cart));

            }else {
                List<Item> cart= (List<Item>) session.getAttribute("cart");
                int index= isExits(masanpham,cart);

                if(index==-1){
                    cart.add(new Item(sanphamRepository.findById(masanpham).get(),1));

                }else {
                    int soluong=cart.get(index).getSoluong()+1;
                    cart.get(index).setSoluong(soluong);

                }
                model.addAttribute("tien",tongtiengiohang(cart));
            }
            return "giohang";
        }

        @RequestMapping(value = "/giohang/xoa/{masanpham}",method = RequestMethod.GET)
        public String xoagiohang(Model model,@PathVariable(value = "masanpham") Long masanpham, HttpSession session,Principal principal){
            if(principal == null){
                model.addAttribute("username", "");
            }else {
                String name = principal.getName();
                model.addAttribute("username", name);
            }

            List<Item> cart= (List<Item>) session.getAttribute("cart");
            int index= isExits(masanpham,cart);
            cart.remove(index);
            session.setAttribute("cart",cart);
            model.addAttribute("tien",tongtiengiohang(cart));
            return "giohang";
        }


        @RequestMapping(value = "/giohang/update",method = RequestMethod.POST)
        public String updategiohang(Model model,HttpServletRequest request, HttpSession session,Principal principal){
            if(principal == null){
                model.addAttribute("username", "");
            }else {
                String name = principal.getName();
                model.addAttribute("username", name);
            }

            String[] quantities= request.getParameterValues("soluong");
            List<Item> cart= (List<Item>) session.getAttribute("cart");
            for(int i=0;i<cart.size();i++){
               cart.get(i).setSoluong(Integer.parseInt(quantities[i]));
            }
            session.setAttribute("cart",cart);
            model.addAttribute("tien",tongtiengiohang(cart));
            return "giohang";
        }

        public double tongtiengiohang(List<Item> cart){
            double tongtien=0;
            for(int i=0;i<cart.size();i++){
                tongtien+=cart.get(i).getSoluong()*(cart.get(i).getSanpham().getDongia()-cart.get(i).getSanpham().getDongia()*cart.get(i).getSanpham().getKhuyenmai()/100);
            }
            return tongtien;
        }

        public int isExits(Long id,List<Item> cart){
            for(int i=0;i<cart.size();i++){
                if(cart.get(i).getSanpham().getMasanpham()==id){
                    return i;
                }
            }
            return -1;
        }

        public int isExitsyeuthich(Long id,List<Yeuthich> list){
            for(int i=0;i<list.size();i++){
                if(list.get(i).getSanpham().getMasanpham()==id){
                    return i;
                }
            }
            return -1;
        }

        @GetMapping(value="/thanhtoan")
        public String thanhtoan(Model model,HttpSession session,Principal principal
        ,@Param("tenkh") String tenkh,@Param("email") String email){
            if(principal == null) {

                model.addAttribute("username", "");
                return "dangnhap";

            }else{

                List<Item> cart= (List<Item>) session.getAttribute("cart");



                if(cart==null){
                    session.setAttribute("cart",cart);
                    model.addAttribute("tien",0);
                }else{
                    session.setAttribute("cart",cart);
                    model.addAttribute("tien",tongtiengiohang(cart));
                }

                model.addAttribute("username",principal.getName());
                model.addAttribute("tenkh",findkhachhangbyusername(principal.getName()).getTenkhachhang());
                model.addAttribute("email",findnguoidungbyusername(principal.getName()).getEmail());
                return "thanhtoan";



            }
        }


        public Khachhang findkhachhangbyusername(String username){
            Khachhang khachhang = khachhangRepository.findKhachhangByUsername(username);
            return khachhang;
        }

        public Nguoidung findnguoidungbyusername(String username){
            Nguoidung khachhang = nguoidungRepository.findBytaikhoan(username);
            return khachhang;
        }


        @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
        public String confirmUserAccount(Model model, @RequestParam("token")String confirmationToken)
        {
            Nguoidung users = nguoidungRepository.findByMaxacthuc(confirmationToken);
            if(users.getMa() != null)
            {
                users.setTrangthai(true);
                nguoidungRepository.save(users);
                model.addAttribute("error", "Xác thực email thành công");
                return "dangnhap";
            }
            return "redirect:/";
        }

        @RequestMapping(value="/timspbygia",method = RequestMethod.GET)
        public String timspbygia(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                 @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                 @RequestParam(name="from") double from,@RequestParam(name="to") double to,Model model,Principal principal){
            Pageable pageable = PageRequest.of(page, size);
            Page<Sanpham> sanphams = sanphamRepository.timspbygia(from,to,pageable);
            model.addAttribute("sanphams", sanphams);
            model.addAttribute("username", principal.getName());
            model.addAttribute("pagenumber",sanphams.getPageable().getPageNumber());
            int totalpage = sanphams.getTotalPages();
            if(totalpage==0){
                totalpage =1;
                model.addAttribute("totalpage",totalpage);
            }else{
                model.addAttribute("totalpage",totalpage);
            }
            return "cuahang";
        }


        @RequestMapping(value = "/themdanhgia", method = RequestMethod.POST)
        public String themdanhgia(Model model,Danhgia danhgia,Principal principal){

            if(principal == null){
                model.addAttribute("username","");
                return "dangnhap";

            }else {

                    if(findkhachhangbyusername(principal.getName())==null){
                        return "redirect:/";
                    }else {

                        model.addAttribute("danhmucs", danhmucRepository.findAll());
                        danhgia.setThoigian(LocalDate.now());
                        danhgia.setKhachhang(findkhachhangbyusername(principal.getName()));
                        model.addAttribute("username", principal.getName());


                        danhgiaRepository.save(danhgia);

                        model.addAttribute("danhgia", new Danhgia());
                        return "redirect:/chitietsanpham/" + danhgia.getSanpham().getMasanpham();
                    }

            }
        }



    }
