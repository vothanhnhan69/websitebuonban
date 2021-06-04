package com.iuh.Controller;

import com.iuh.Entity.*;

import com.iuh.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	ThuonghieuRepository thuonghieuRepository;


	@Autowired
	DanhmucRepository danhmucRepository;
	@Autowired
	NguoibanRepository nguoibanRepository;
	@Autowired
	KhachhangRepository khachhangRepository;
	@Autowired
	NguoidungRepository nguoidungRepository;
	@Autowired
	SanphamRepository sanphamRepository;

	@Autowired
	LoaidanhmucRepository loaidanhmucRepository;




	//-----------------------Quan ly thuong hieu ---------------------//
	@GetMapping(value="/quanlythuonghieu/listthuonghieu")
	public List<Thuonghieu> quanlythuonghieu(){
		return thuonghieuRepository.findAll();
	}



//	@PostMapping(value = "/quanlythuonghieu/savethuonghieu")
    @RequestMapping(value = "/quanlythuonghieu/savethuonghieu", method = RequestMethod.POST,  consumes={"application/json"})
	public Thuonghieu savethuonghieu(@RequestBody Thuonghieu thuonghieu ){
		System.out.println("sdfdsfsf");
		return thuonghieuRepository.save(thuonghieu);

	}





	@PutMapping(value = "/quanlythuonghieu/update/{id}")
	public Thuonghieu update(@RequestBody Thuonghieu model, @PathVariable("id") long id) {
		model.setMathuonghieu(id);
		return thuonghieuRepository.save(model);
	}


	@GetMapping(value = "/quanlythuonghieu/delete/{id}")
	public void deletethuonghieu(@PathVariable("id") long id){
		thuonghieuRepository.deleteById(id);

	}

	@GetMapping(value = "/quanlythuonghieu/find/{id}")
	public Thuonghieu findthuonghieubyid(@PathVariable("id") long id){
		return thuonghieuRepository.findById(id).get();
	}




	//----------------------- Quan ly danh muc -------------------------//
	@GetMapping(value="/quanlydanhmuc/listdanhmuc")
	public List<Danhmuc> quanlydanhmuc(){
		return danhmucRepository.findAll();
	}
	///
	@RequestMapping(value = "/quanlydanhmuc/savedanhmuc", method = RequestMethod.POST,  consumes={"application/json"})
	public Danhmuc savedanhmuc(@RequestBody Danhmuc danhmuc ){
		return danhmucRepository.save(danhmuc);

	}


	@PutMapping(value = "/quanlydanhmuc/update/{id}")
	public Danhmuc update(@RequestBody Danhmuc model, @PathVariable("id") long id) {
		model.setMadanhmuc(id);
		return danhmucRepository.save(model);
	}


	@GetMapping(value = "/quanlydanhmuc/delete/{id}")
	public void deletedanhmuc(@PathVariable("id") long id){
		danhmucRepository.deleteById(id);
	}
	@GetMapping(value = "/quanlydanhmuc/find/{id}")
	public Danhmuc finddanhmucbyid(@PathVariable("id") long id){
		return danhmucRepository.findById(id).get();
	}
	//----------------------- Quan ly nguoi ban -------------------------//
	@GetMapping(value="/quanlynguoiban/listnguoiban")
	public List<Nguoiban> listnguoiban(){
		return nguoibanRepository.findAll();
	}
	///
	@RequestMapping(value = "/quanlynguoiban/savenguoiban", method = RequestMethod.POST,  consumes={"application/json"})
	public Nguoiban savenguoiban(@RequestBody Nguoiban nguoiban ){
		System.out.println("sdfdsfsf");
		return nguoibanRepository.save(nguoiban);

	}
	@PutMapping(value = "/quanlynguoiban/update/{id}")
	public Nguoiban updatenguoiban(@RequestBody Nguoiban model, @PathVariable("id") long id) {
		model.setManguoiban(id);
		model.setNguoidung(nguoibanRepository.timnguoibanbynguoidung(nguoibanRepository.findById(id).get().getNguoidung().getMa()).getNguoidung());
	//	model.setNguoidung(nguoibanRepository.timnguoidungid(id));
	//	model.setNguoidung();
		return nguoibanRepository.save(model);
	}





	@GetMapping(value = "/quanlynguoiban/delete/{id}")
	public void deletenguoiban(@PathVariable("id") long id){
		nguoibanRepository.deleteById(id);
	}
	@GetMapping(value = "/quanlynguoiban/find/{id}")
	public Nguoiban findnguoibanbyid(@PathVariable("id") long id){
		return nguoibanRepository.findById(id).get();
	}
	//----------------------------- Quản lý khách hàng --------------------------//
	@GetMapping(value="/quanlynguoimua/listnguoimua")
	public List<Khachhang> listnguoimua(){
		return khachhangRepository.findAll();
	}
	@GetMapping(value = "/quanlynguoimua/find/{id}")
	public Khachhang findnguoimuabyid(@PathVariable("id") long id){
		return khachhangRepository.findById(id).get();
	}
	
	@PutMapping(value = "/quanlynguoimua/update/{id}")
	public Khachhang updatenguoimua(@RequestBody Khachhang model, @PathVariable("id") long id) {
		model.setMakhachhang(id);
	//	model.setNguoidung(nguoibanRepository.timnguoibanbynguoidung(nguoibanRepository.findById(id).get().getNguoidung().getMa()).getNguoidung());
		model.setNguoidung(khachhangRepository.timnguoimuabynguoidung(khachhangRepository.findById(id).get().getNguoidung().getMa()).getNguoidung());
		//model.setDsdanhgia(danhgiaRepository.timdanhgiabykhachhang(id));
	//	model.setNguoidung(nguoibanRepository.timnguoidungid(id));
	//	model.setNguoidung();
		
		return khachhangRepository.save(model);
	}

	//----------------------Quan ly nguoi dung ------------------------//
	@GetMapping(value="/quanlynguoidung/listnguoidung")
	public List<Nguoidung> listnguoidung(){
		return nguoidungRepository.findAll();
	}
	@GetMapping(value = "/quanlynguoidung/find/{id}")
	public Nguoidung findnguoidungbyid(@PathVariable("id") long id){
		return nguoidungRepository.findById(id).get();
	}
	@PutMapping(value = "/quanlynguoidung/update/{id}")
	public Nguoidung updatenguoidung(@RequestBody Nguoidung model, @PathVariable("id") long id) {
		model.setMa(id);
	//	model.setNguoidung(nguoibanRepository.timnguoibanbynguoidung(nguoibanRepository.findById(id).get().getNguoidung().getMa()).getNguoidung());
		//model.setNguoidung(khachhangRepository.timnguoimuabynguoidung(khachhangRepository.findById(id).get().getNguoidung().getMa()).getNguoidung());
		//model.setDsdanhgia(danhgiaRepository.timdanhgiabykhachhang(id));
	//	model.setNguoidung(nguoibanRepository.timnguoidungid(id));
	//	model.setNguoidung();
		
		return nguoidungRepository.save(model);
	}
	//-----------------------------quan ly san pham -----------------------------//
	@GetMapping(value="/quanlysanpham/listsanpham")
	public List<Sanpham> listsanpham(){
		return sanphamRepository.findAll();
	}
	@GetMapping(value = "/quanlysanpham/find/{id}")
	public Sanpham findsanphambyid(@PathVariable("id") long id){
		return sanphamRepository.findById(id).get();
	}
	@PutMapping(value = "/quanlysanpham/update/{id}")
	public Sanpham updatesanpham(@RequestBody Sanpham model, @PathVariable("id") long id) {
	//	model = sanphamRepository.findById(id).get();
	//	model.setTrangthai(trangthai);
		model.setMasanpham(id);
		model.setTensanpham(sanphamRepository.findById(id).get().getTensanpham());
		model.setMota(sanphamRepository.findById(id).get().getMota());
		model.setSoluong(sanphamRepository.findById(id).get().getSoluong());
		model.setHinhanh(sanphamRepository.findById(id).get().getHinhanh());
		model.setKichthuoc(sanphamRepository.findById(id).get().getKichthuoc());
		model.setMausac(sanphamRepository.findById(id).get().getMausac());
		model.setDongia(sanphamRepository.findById(id).get().getDongia());
		model.setKhuyenmai(sanphamRepository.findById(id).get().getKhuyenmai());
		
	//	model = sanphamRepository.findById(id).get();
		
		
	//	model.setLoaidanhmuc(sanphamRepository.timdanhmucbysanpham(sanphamRepository.findById(id).get().getLoaidanhmuc().getMaloaidanhmuc()).getLoaidanhmuc());
//		System.out.println(model.getLoaidanhmuc());
		model.setLoaidanhmuc(sanphamRepository.findById(id).get().getLoaidanhmuc());
		model.setNguoiban(sanphamRepository.findById(id).get().getNguoiban());
		model.setThuonghieu(sanphamRepository.findById(id).get().getThuonghieu());
	//	model.setNguoiban(sanphamRepository.findById(id).get().getNguoiban());
		System.out.println(model);
		return sanphamRepository.save(model);
	


	}

	@GetMapping(value="/quanlyloaidanhmuc/listloaidanhmuc")
	public List<Loaidanhmuc> quanlyloaidanhmuc(){
		return loaidanhmucRepository.findAll();
	}


	@RequestMapping(value = "/quanlyloaidanhmuc/saveloaidanhmuc/{id}", method = RequestMethod.POST,  consumes={"application/json"})
	public Loaidanhmuc saveloaidanhmuc(@RequestBody Loaidanhmuc loaidanhmuc, @PathVariable("id") long id ){
		loaidanhmuc.setDanhmuc(danhmucRepository.findById(id).get());
		return loaidanhmucRepository.save(loaidanhmuc);
	}


	@PutMapping(value = "/quanlyloaidanhmuc/update/{id}")
	public Loaidanhmuc update(@RequestBody Loaidanhmuc model, @PathVariable("id") long id) {
		model.setMaloaidanhmuc(loaidanhmucRepository.findById(id).get().getMaloaidanhmuc());
		model.setDanhmuc(loaidanhmucRepository.findById(id).get().getDanhmuc());
		model.setDssanpham(loaidanhmucRepository.findById(id).get().getDssanpham());
		return loaidanhmucRepository.save(model);
	}


	@GetMapping(value = "/quanlyloaidanhmuc/delete/{id}")
	public void deleteloaidanhmuc(@PathVariable("id") long id){
		loaidanhmucRepository.deleteById(id);
	}

	@GetMapping(value = "/quanlyloaidanhmuc/find/{id}")
	public Loaidanhmuc findloaidanhmucbyid(@PathVariable("id") long id){
		return loaidanhmucRepository.findById(id).get();
	}

}
