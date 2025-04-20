package com.lucamanfroi.startuprush.repository;

import com.lucamanfroi.startuprush.domain.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface StartupRepository extends JpaRepository<Startup, Long> {
    boolean existsByName(String name);
    boolean existsBySlogan(String slogan);

}
