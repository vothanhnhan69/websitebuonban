package com.iuh.Repository;

import com.iuh.Entity.Hoadon;
import com.iuh.Entity.Yeuthich;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface YeuthichRepository extends JpaRepository<Yeuthich,Long> {

    @Query("SELECT s FROM Yeuthich s where s.khachhang.nguoidung.taikhoan=:username")
    Page<Yeuthich> listyeuthichbykh(String username, Pageable pageable);

    @Query("SELECT s FROM Yeuthich s where s.khachhang.nguoidung.taikhoan=:username")
    List<Yeuthich> listyeuthich(String username);

    @Query("SELECT s FROM Yeuthich s where s.sanpham.masanpham=:masanpham and s.khachhang.makhachhang=:makhachhang")
    Yeuthich findYeuthichBymasanpham(Long masanpham,Long makhachhang);

}
