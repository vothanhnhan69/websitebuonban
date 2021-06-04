package com.iuh.Repository;

import com.iuh.Entity.Khachhang;
import com.iuh.Entity.Nguoiban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NguoibanRepository extends JpaRepository<Nguoiban,Long> {
	  @Query("select t from Nguoiban t where t.nguoidung.ma=:manguoidung")
	    Nguoiban timnguoibanbynguoidung(Long manguoidung);


	@Query("select kh from Nguoiban kh join Nguoidung nd on kh.nguoidung.ma=nd.ma where nd.taikhoan=:username")
	Nguoiban findNguoibanByUsername(String username);
}
