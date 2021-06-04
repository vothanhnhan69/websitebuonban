package com.iuh.Repository;

import com.iuh.Entity.Hoadon;
import com.iuh.Entity.Sanpham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoadonRepository extends JpaRepository<Hoadon,Long> {

    @Query("SELECT s FROM Hoadon s where s.khachhang.nguoidung.taikhoan=:username")
    Page<Hoadon> listhdbykh(String username, Pageable pageable);







}
