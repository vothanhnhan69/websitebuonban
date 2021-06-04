package com.iuh.Controller;

import com.iuh.Entity.*;
import com.iuh.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping(value = "/nguoiban")
public class NguoibanController {

    @Autowired
    SanphamRepository sanphamRepository;
    @Autowired
    DanhmucRepository danhmucRepository;
    @Autowired
    ThuonghieuRepository thuonghieuRepository;
    @Autowired
    LoaidanhmucRepository loaidanhmucRepository;

    @Autowired
    ChitiethoadonRepository chitiethoadonRepository;

    @Autowired
    HoadonRepository hoadonRepository;

    @Autowired
    NguoidungRepository nguoidungRepository;

    @Autowired
    DiachiRepository diachiRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    NguoibanRepository nguoibanRepository;

//    private static String UPLOADED_FOLDER = "C:\\Users\\Thanh Nhan\\Desktop\\Doantotnghiep\\backup\\websitebuonban\\src\\main\\resources\\static\\plugins\\images\\users\\";

    //chuyen trang quan ly thuong hieu
    @GetMapping(value = "/quanlysanpham")
    public String quanlythuonghieu(Model model, @Param("tensp") String tensp, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                   @RequestParam(name = "size", required = false, defaultValue = "10") Integer size, Principal principal) {

        if (tensp == null) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Sanpham> sppage = sanphamRepository.listspbynguoiban(findnguoibanbyusername(principal.getName()).getManguoiban(), pageable);
            model.addAttribute("sanphams", sppage);
            model.addAttribute("pagenumber", sppage.getPageable().getPageNumber());
            model.addAttribute("errorsanpham", null);
            int totalpage = sppage.getTotalPages();
            if (totalpage == 0) {
                totalpage = 1;
                model.addAttribute("totalpage", totalpage);
            } else {
                model.addAttribute("totalpage", totalpage);
            }

            return "/nguoiban/quanlysanpham";
        } else {
            Pageable pageable = PageRequest.of(page, size);
            Page<Sanpham> thuonghieus = sanphamRepository.timsanphambyten1(tensp, pageable);
            model.addAttribute("sanphams", thuonghieus);
            model.addAttribute("pagenumber", thuonghieus.getPageable().getPageNumber());
            model.addAttribute("errorsanpham", null);
            int totalpage = thuonghieus.getTotalPages();
            if (totalpage == 0) {
                totalpage = 1;
                model.addAttribute("totalpage", totalpage);
            } else {
                model.addAttribute("totalpage", totalpage);
            }
            return "/nguoiban/quanlysanpham";
        }
    }


    @GetMapping(value = "/themsanpham")
    public String chuyentragnthemsp(Model model) {
        model.addAttribute("sanpham", new Sanpham());
        model.addAttribute("loaidanhmuc", loaidanhmucRepository.findAll());
        model.addAttribute("thuonghieu", thuonghieuRepository.findAll());
        model.addAttribute("error", null);
        return "/nguoiban/themsanpham";
    }


    //them san pham
    @RequestMapping(value = "/btnthemsanpham", method = RequestMethod.POST)
    public String themsanpham(Sanpham sanpham, @RequestParam("imageFile") MultipartFile file, Principal principal,Model model) throws IOException {
        sanpham.setNguoiban(nguoibanRepository.findNguoibanByUsername(principal.getName()));
        sanpham.setHinhanh(file.getOriginalFilename());
        sanpham.setTrangthai("false");
        sanpham.setNguoiban(findnguoibanbyusername(principal.getName()));
        sanphamRepository.save(sanpham);
//
//        byte[] bytes = file.getBytes();
//        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
//        Files.write(path, bytes);


        Path path = Paths.get("images/");


//        Path path1 = Paths.get("uploads/");

        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream,path.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);



        }catch ( Exception e){

        }



        return "redirect:/nguoiban/quanlysanpham";
    }


    @RequestMapping(value = "/capnhatsanpham", method = RequestMethod.GET)
    public String chuyentrangcapnhatsp(@RequestParam("masanpham") Long id, Model model) {
        Optional<Sanpham> th = sanphamRepository.findById(id);
        th.ifPresent(item -> model.addAttribute("sanpham", th));
        model.addAttribute("loaidanhmuc", loaidanhmucRepository.findAll());
        model.addAttribute("thuonghieu", thuonghieuRepository.findAll());
        model.addAttribute("error", null);
        return "/nguoiban/capnhatsanpham";
    }


    @RequestMapping(value = "/capnhatchitiethoadon", method = RequestMethod.GET)
    public String capnhatchitiethoadon(@RequestParam("machitiethoadon") Long id, Model model) {
        Optional<Chitiethoadon> cthd = chitiethoadonRepository.findById(id);
        cthd.ifPresent(item -> model.addAttribute("chitiethoadon", cthd));
        return "/nguoiban/capnhatchitiethoadon";
    }


    //them san pham
    @RequestMapping(value = "/capnhatcthd", method = RequestMethod.POST)
    public String capnhatcthd(Chitiethoadon cthd) {
        chitiethoadonRepository.save(cthd);
        return "redirect:/nguoiban/quanlydonhang";
    }

    //them san pham
    @RequestMapping(value = "/capnhatsanpham", method = RequestMethod.POST)
    public String capnhatsanpham(Sanpham sanpham,Model model) {
        sanphamRepository.save(sanpham);
        return "redirect:/nguoiban/quanlysanpham";
    }

    //xoa sanpham
    @RequestMapping(value = "/xoasanpham", method = RequestMethod.GET)
    public String xoadanhmuc(@RequestParam("masanpham") Long id, Model model,@Param("tensp") String tensp, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                             @RequestParam(name = "size", required = false, defaultValue = "10") Integer size, Principal principal) {
        Optional<Sanpham> dm = sanphamRepository.findById(id);

        try {
            sanphamRepository.delete(dm.get());
            model.addAttribute("errorsanpham", "Xóa thành công");

            Pageable pageable = PageRequest.of(page, size);
            Page<Sanpham> sppage = sanphamRepository.listspbynguoiban(findnguoibanbyusername(principal.getName()).getManguoiban(), pageable);
            model.addAttribute("sanphams", sppage);
            model.addAttribute("pagenumber", sppage.getPageable().getPageNumber());
            int totalpage = sppage.getTotalPages();
            if (totalpage == 0) {
                totalpage = 1;
                model.addAttribute("totalpage", totalpage);
            } else {
                model.addAttribute("totalpage", totalpage);
            }
        }catch (Exception e){
            model.addAttribute("errorsanpham", "Không thể xóa vì sản phẩm đã được thanh toán");
            Pageable pageable = PageRequest.of(page, size);
            Page<Sanpham> sppage = sanphamRepository.listspbynguoiban(findnguoibanbyusername(principal.getName()).getManguoiban(), pageable);
            model.addAttribute("sanphams", sppage);
            model.addAttribute("pagenumber", sppage.getPageable().getPageNumber());
            int totalpage = sppage.getTotalPages();
            if (totalpage == 0) {
                totalpage = 1;
                model.addAttribute("totalpage", totalpage);
            } else {
                model.addAttribute("totalpage", totalpage);
            }
        }



        return "/nguoiban/quanlysanpham";
    }


    public Nguoiban findnguoibanbyusername(String username) {
        Nguoiban nguoiban = nguoibanRepository.findNguoibanByUsername(username);
        return nguoiban;
    }


    //chuyen trang quan ly thuong hieu
    @GetMapping(value = "/quanlydonhang")
    public String quanlydonhang(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(name = "size", required = false, defaultValue = "10") Integer size, Principal principal) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Chitiethoadon> sppage = chitiethoadonRepository.listcthdbynguoiban(findnguoibanbyusername(principal.getName()).getManguoiban(), pageable);

        model.addAttribute("chitiethoadons", sppage);
        model.addAttribute("pagenumber", sppage.getPageable().getPageNumber());


        int totalpage = sppage.getTotalPages();
        if (totalpage == 0) {
            totalpage = 1;
            model.addAttribute("totalpage", totalpage);
        } else {
            model.addAttribute("totalpage", totalpage);
        }

        return "/nguoiban/quanlydonhang";
    }

    @GetMapping(value = "/thongkebaocao")
    public String statistical(Model model, @Param("daystart") String daystart, @Param("dayend") String dayend,
                              Principal principal) {
        if(daystart==null && dayend==null){
            LocalDate ngaybatdau=LocalDate.of(2021,3,30);
            LocalDate ngayketthuc=LocalDate.now();

            String[] partstart = String.valueOf(ngaybatdau).split("-");
            String monthstart = partstart[1]; // 004
            String yearstart = partstart[0];
            LocalDate start = LocalDate.of(Integer.valueOf(yearstart),Integer.valueOf(monthstart),1);

            String[] partsend = String.valueOf(ngayketthuc).split("-");
            String monthend = partsend[1]; // 004
            String yearsend = partsend[0];
            LocalDate end = LocalDate.of(Integer.valueOf(yearsend),Integer.valueOf(monthend),1);
            List<LocalDate> totalsMonth = new ArrayList<>();

            while (!start.isAfter(end)) {
                totalsMonth.add(start);
                start = start.plusMonths(1);
            }

            Map<String, Double> ds = new LinkedHashMap<>();
            SimpleDateFormat sf= new SimpleDateFormat("MM/yyyy");


            for (int i = 0; i < totalsMonth.size(); i++) {
                LocalDate date=totalsMonth.get(i);
                try{
                    double x = chitiethoadonRepository.danhthutheothang(date.getMonthValue(),date.getYear(),findnguoibanbyusername(principal.getName()).getManguoiban(),"Đã thanh toán");
                    ds.put(sf.format(java.sql.Date.valueOf(totalsMonth.get(i))), chitiethoadonRepository.danhthutheothang(date.getMonthValue(), date.getYear(),findnguoibanbyusername(principal.getName()).getManguoiban(),"Đã thanh toán"));
                }catch (Exception e){
                    ds.put(sf.format(Date.valueOf(totalsMonth.get(i))), 0.0);
                };
            }
            model.addAttribute("listchart", ds);
            return "/nguoiban/thongkebaocao";
        }else if(daystart.length() == 7 && dayend.length() == 7){
            String[] partstart = daystart.split("-");
            String monthstart = partstart[1]; // 004
            String yearstart = partstart[0];
            LocalDate start = LocalDate.of(Integer.valueOf(yearstart),Integer.valueOf(monthstart),1);

            String[] partsend = dayend.split("-");
            String monthend = partsend[1]; // 004
            String yearsend = partsend[0];
            LocalDate end = LocalDate.of(Integer.valueOf(yearsend),Integer.valueOf(monthend),1);
            List<LocalDate> totalsMonth = new ArrayList<>();

            while (!start.isAfter(end)) {
                totalsMonth.add(start);
                start = start.plusMonths(1);
            }

            Map<String, Double> ds = new LinkedHashMap<>();
            SimpleDateFormat sf= new SimpleDateFormat("MM/yyyy");

            for (int i = 0; i < totalsMonth.size(); i++) {
                LocalDate date=totalsMonth.get(i);
                try{
                    double x = chitiethoadonRepository.danhthutheothang(date.getMonthValue(),date.getYear(),findnguoibanbyusername(principal.getName()).getManguoiban(),"Đã thanh toán");
                    ds.put(sf.format(java.sql.Date.valueOf(totalsMonth.get(i))), chitiethoadonRepository.danhthutheothang(date.getMonthValue(), date.getYear(),findnguoibanbyusername(principal.getName()).getManguoiban(),"Đã thanh toán"));
                }catch (Exception e){
                    ds.put(sf.format(Date.valueOf(totalsMonth.get(i))), 0.0);
                };
            }

            model.addAttribute("listchart", ds);
            return "/nguoiban/thongkebaocao";
        }else {
            int yearstart=Integer.valueOf(daystart);
            int yearend=Integer.valueOf(dayend);
            Map<String, Double> ds = new LinkedHashMap<>();
            for (int i = yearstart; i <=yearend ; i++) {
                try{
                    double x = chitiethoadonRepository.danhthutheonam(i,findnguoibanbyusername(principal.getName()).getManguoiban(),"Đã thanh toán");
                    ds.put(String.valueOf(i), chitiethoadonRepository.danhthutheonam(i,findnguoibanbyusername(principal.getName()).getManguoiban(),"Đã thanh toán"));
                }catch (Exception e){
                    ds.put(String.valueOf(i), 0.0);
                };
            }
            model.addAttribute("listchart", ds);
            return "/nguoiban/thongkebaocao";
        }
    }

    public Nguoidung findnguoidungbyusername(String username){
        Nguoidung khachhang = nguoidungRepository.findBytaikhoan(username);
        return khachhang;
    }


    @RequestMapping(value = "/thongtincanhan",method = RequestMethod.GET)
    public  String capnhatdiachinguoiban(Model model,Principal principal){
        Nguoiban kh = findnguoibanbyusername(principal.getName());

        model.addAttribute("username",principal.getName());
        model.addAttribute("diachi",diachiRepository.finddiachibynguoidung(kh.getNguoidung().getMa()));
        model.addAttribute("nguoiban", kh);
        model.addAttribute("error2",null);
        return "/nguoiban/capnhattaikhoannguoiban";
    }

    @RequestMapping(value = "updatetaikhoan", method = RequestMethod.POST)
    public String capnhattaikhoannguoiban(Nguoiban nguoiban,Model model,Principal principal) {
        nguoibanRepository.save(nguoiban);
        model.addAttribute("username",principal.getName());
        model.addAttribute("diachi",diachiRepository.finddiachibynguoidung(nguoiban.getNguoidung().getMa()));
        model.addAttribute("nguoiban", nguoiban);
        model.addAttribute("error2","Cập nhật thành công");

        return "/nguoiban/capnhattaikhoannguoiban";
    }

    @RequestMapping(value = "/capnhatdiachinguoiban",method = RequestMethod.GET)
    public  String capnhatdiachi(Model model,Principal principal){
        model.addAttribute("diachi",findnguoidungbyusername(principal.getName()).getDsdiachi().get(0));
        model.addAttribute("username", principal.getName());
        return "/nguoiban/capnhatdiachinguoiban";
    }


    @RequestMapping(value = "/updatediachinguoiban" ,method = RequestMethod.POST)
    public  String updatediachi(Diachi diachi,Model model,Principal principal){


        diachiRepository.save(diachi);
        model.addAttribute("error2","Cập nhật địa chỉ thành công");
        Nguoiban kh = findnguoibanbyusername(principal.getName());
        model.addAttribute("username",principal.getName());
        model.addAttribute("diachi",diachiRepository.finddiachibynguoidung(kh.getNguoidung().getMa()));
        model.addAttribute("nguoiban", kh);

        return "/nguoiban/capnhattaikhoannguoiban";
    }

    @GetMapping(value="/doimatkhaunguoiban")
    public String doimatkhau(Model model,Principal principal){

        model.addAttribute("nguoidung", findnguoidungbyusername(principal.getName()));
        model.addAttribute("username",principal.getName());
        return "/nguoiban/doimatkhaunguoiban";
    }

    @RequestMapping(value="updatepasswordnguoiban",method = RequestMethod.POST)
    public String doimatkhau(Model model,Nguoidung nguoidung,Principal principal){
        Nguoiban kh = findnguoibanbyusername(principal.getName());

        model.addAttribute("username",principal.getName());
        model.addAttribute("diachi",diachiRepository.finddiachibynguoidung(kh.getNguoidung().getMa()));
        model.addAttribute("error2","Đổi mật khẩu thành công");
        model.addAttribute("nguoiban", kh);
        nguoidung.setVaitros(findnguoidungbyusername(principal.getName()).getVaitros());
        nguoidung.setMatkhau(passwordEncoder.encode(nguoidung.getMatkhau()));
        nguoidungRepository.save(nguoidung);

        return "/nguoiban/capnhattaikhoannguoiban";
    }
}
