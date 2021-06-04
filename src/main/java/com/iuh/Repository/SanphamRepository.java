package com.iuh.Repository;

import com.iuh.Entity.Sanpham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SanphamRepository extends JpaRepository<Sanpham,Long> {
    @Query("SELECT s FROM Sanpham s where s.tensanpham like %:tensp% and s.trangthai=:trangthai and s.soluong>0")
    Page<Sanpham> timsanphambyten(@Param("tensp") String tensp,Pageable pageable,String trangthai);

    @Query("SELECT s FROM Sanpham s where s.tensanpham like %:tensp% ")
    Page<Sanpham> timsanphambyten1(@Param("tensp") String tensp,Pageable pageable);

    @Query("SELECT s FROM Sanpham s where s.trangthai =:trangthai and s.soluong>0")
    Page<Sanpham> timspbytrangthang(String trangthai,Pageable pageable);

    @Query("SELECT s FROM Sanpham s where s.dongia>=:from and s.dongia<=:to")
    Page<Sanpham> timspbygia(double from,double to,Pageable pageable);


    @Query("SELECT s FROM Sanpham s where s.loaidanhmuc.maloaidanhmuc=:maloaidanhmuc")
    Page<Sanpham> listspbydanhmuc(@Param("maloaidanhmuc") Long maloaidanhmuc, Pageable pageable);

    @Query("SELECT s FROM Sanpham s where s.nguoiban.manguoiban=:manguoiban")
    Page<Sanpham> listspbynguoiban(Long manguoiban, Pageable pageable);

    @Query("update Sanpham s set s.soluong=:soluong where s.masanpham=:masanpham")
    void updatesoluongsanpham(Long masanpham,int soluong);


}
