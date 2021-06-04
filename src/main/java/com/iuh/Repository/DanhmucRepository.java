package com.iuh.Repository;

import com.iuh.Entity.Danhmuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhmucRepository extends JpaRepository<Danhmuc,Long> {
    @Query("SELECT d FROM Danhmuc d where d.tendanhmuc like %:temdm%")
    List<Danhmuc> timdanhmucbyten(@Param("temdm") String temdm);
}
