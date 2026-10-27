package com.quickbite.quickbite.repository;

import com.quickbite.quickbite.entity.Menu;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select m from Menu m where m.idMenu = :id")
    Optional<Menu> cariDanKunciStok(@Param("id") String id);
}