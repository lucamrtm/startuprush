package com.lucamanfroi.startuprush.repository;

import com.lucamanfroi.startuprush.domain.Battle;
import com.lucamanfroi.startuprush.domain.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BattleRepository extends JpaRepository<Battle, Long> {

    List<Battle> findByTorneioAndRound(Torneio torneio, Integer round);

    List<Battle> findByTorneio(Torneio torneio);
}
