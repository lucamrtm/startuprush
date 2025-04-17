package com.lucamanfroi.startuprush.repository;

import com.lucamanfroi.startuprush.domain.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TorneioRepository extends JpaRepository<Torneio, Long> {
}

