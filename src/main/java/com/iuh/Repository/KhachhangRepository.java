package com.iuh.Repository;

import com.iuh.Entity.Khachhang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachhangRepository extends JpaRepository<Khachhang,Long> {


	 @Query("select kh from Khachhang kh join Nguoidung nd on kh.nguoidung.ma=nd.ma where nd.taikhoan=:username")
	    Khachhang findKhachhangByUsername(String username);
	    
	    @Query("select t from Khachhang t where t.nguoidung.ma=:manguoidung")
		Khachhang timnguoimuabynguoidung(Long manguoidung);


}
