package com.iuh.Repository;

import com.iuh.Entity.Diachi;
import com.iuh.Entity.Khachhang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiachiRepository extends JpaRepository<Diachi,Long> {

    @Query("select dc from Diachi dc where dc.nguoidung.ma=:manguoidung")
    Diachi finddiachibynguoidung(Long manguoidung);
}
