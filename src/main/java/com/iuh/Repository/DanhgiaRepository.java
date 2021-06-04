package com.iuh.Repository;

import com.iuh.Entity.Danhgia;
import com.iuh.Entity.Sanpham;
import com.iuh.Entity.Yeuthich;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhgiaRepository extends JpaRepository<Danhgia,Long> {

    @Query("SELECT s FROM Danhgia s where s.sanpham.masanpham=:masp")
    List<Danhgia> listdanhgiabysp(Long masp);


    @Query("SELECT s FROM Danhgia s where s.khachhang.makhachhang=:makh")
    Page<Danhgia> listdanhgiabykh(Long makh,Pageable pageable);


    @Query("SELECT s FROM Danhgia s where s.sanpham.masanpham=:masanpham and s.khachhang.makhachhang=:makhachhang")
    Danhgia findDanhgiaBymasanpham(Long masanpham, Long makhachhang);

}
