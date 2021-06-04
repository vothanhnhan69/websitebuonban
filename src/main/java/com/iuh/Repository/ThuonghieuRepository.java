package com.iuh.Repository;

import com.iuh.Entity.Thuonghieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuonghieuRepository extends JpaRepository<Thuonghieu,Long> {
    @Query("SELECT t FROM Thuonghieu t where t.tenthuonghieu like %:tenth%")
    List<Thuonghieu> timthuonghieubyten(@Param("tenth") String tenth);
}
