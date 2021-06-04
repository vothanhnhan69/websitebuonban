package com.iuh.Repository;

import com.iuh.Entity.Loaidanhmuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaidanhmucRepository extends JpaRepository<Loaidanhmuc,Long> {
}
