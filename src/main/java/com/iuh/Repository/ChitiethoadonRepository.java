package com.iuh.Repository;

import com.iuh.Entity.Chitiethoadon;
import com.iuh.Entity.Hoadon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChitiethoadonRepository extends JpaRepository<Chitiethoadon,Long> {
    @Query("SELECT s FROM Chitiethoadon s where s.hoadon.mahd=:mahd")
    List<Chitiethoadon> listcthdbyhd(Long mahd);


    @Query("SELECT s FROM Chitiethoadon s where s.sanpham.nguoiban.manguoiban=:manguoiban")
    Page<Chitiethoadon> listcthdbynguoiban(Long manguoiban, Pageable pageable);




    @Query(value= "select sum(cthd.dongia*cthd.soluong) from chitiethoadon cthd left join hoadon hd on cthd.hoadon_id=hd.mahd\n" +
            "left join sanpham sp on cthd.sanpham_id=sp.masanpham where year(hd.ngaylaphd)=:year and month(hd.ngaylaphd)=:month and sp.nguoiban_id=:manguoiban and cthd.trangthai=:trangthai",nativeQuery = true)
    double danhthutheothang(int month,int year,Long manguoiban,String trangthai);

    @Query(value= "select sum(cthd.dongia*cthd.soluong) from chitiethoadon cthd left join hoadon hd on cthd.hoadon_id=hd.mahd\n" +
            "left join sanpham sp on cthd.sanpham_id=sp.masanpham where year(hd.ngaylaphd)=:year and sp.nguoiban_id=:manguoiban and cthd.trangthai=:trangthai",nativeQuery = true)
    double danhthutheonam(int year,Long manguoiban,String trangthai);

}
