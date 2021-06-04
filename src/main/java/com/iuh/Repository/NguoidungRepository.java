package com.iuh.Repository;

import com.iuh.Entity.Nguoidung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NguoidungRepository extends JpaRepository<Nguoidung,Long> {

//    @Query("select t from nguoidung t where t.taikhoan=:taikhoan")
    Nguoidung findBytaikhoan(String taikhoan);
    Optional<Nguoidung> findByEmail(String email);
    Nguoidung findByMaxacthuc(String maxacthuc);
}
