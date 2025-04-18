package com.lucamanfroi.startuprush.services;

import com.lucamanfroi.startuprush.domain.Startup;
import com.lucamanfroi.startuprush.domain.Torneio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BattleService {

    @Autowired
    private TorneioService torneioService; // para atualizar o torneio

    private final Random random = new Random();

    public void applyEvent(Startup startup, String event) {
        switch (event.toLowerCase()) {
            case "pitch_convincente":
                startup.setScore(startup.getScore() + 6);
                break;
            case "produto_com_bugs":
                startup.setScore(startup.getScore() - 4);
                break;
            case "boa_tracao":
                startup.setScore(startup.getScore() + 3);
                break;
            case "investidor_irritado":
                startup.setScore(startup.getScore() - 6);
                break;
            case "fake_news":
                startup.setScore(startup.getScore() - 8);
                break;
            default:
                throw new IllegalArgumentException("Evento inválido: " + event);
        }
    }

    public Startup resolveBattle(Torneio torneio, Startup s1, Startup s2) {
        Startup winner;
        if (s1.getScore() > s2.getScore()) {
            winner = s1;
        } else if (s2.getScore() > s1.getScore()) {
            winner = s2;
        } else {
            if (random.nextBoolean()) {
                s1.setScore(s1.getScore() + 2);
                winner = s1;
            } else {
                s2.setScore(s2.getScore() + 2);
                winner = s2;
            }
        }

        winner.setScore(winner.getScore() + 30); // +30 pontos pela vitória
        torneio.getRanking().add(winner);         // ADICIONA no ranking do torneio

        torneioService.save(torneio);              // Salva o torneio atualizado
        return winner;
    }


}














