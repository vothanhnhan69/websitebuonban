package com.iuh.Repository;

import com.iuh.Entity.Vaitro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaitroRepository extends JpaRepository<Vaitro,Long> {

    Vaitro findByten(String name);
}
