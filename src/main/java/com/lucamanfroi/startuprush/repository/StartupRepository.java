package com.lucamanfroi.startuprush.repository;

import com.lucamanfroi.startuprush.domain.Startup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StartupRepository extends JpaRepository<Startup, Long> {
}
