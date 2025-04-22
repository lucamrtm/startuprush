package com.lucamanfroi.startuprush.services;

import com.lucamanfroi.startuprush.domain.Battle;
import com.lucamanfroi.startuprush.domain.Evento;
import com.lucamanfroi.startuprush.domain.Startup;
import com.lucamanfroi.startuprush.domain.Torneio;
import com.lucamanfroi.startuprush.dto.BattleResolutionRequest;
import com.lucamanfroi.startuprush.repository.BattleRepository;
import com.lucamanfroi.startuprush.repository.TorneioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BattleService {
    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private TorneioRepository torneioRepository;

    @Autowired
    private TorneioService torneioService; // para atualizar o torneio

    @Autowired
    private StartupService startupService;



    private final Random random = new Random();

    public Battle save(Battle battle) { return battleRepository.save(battle); }
    public List<Battle> findAll() { return battleRepository.findAll(); }
    public Optional<Battle> findById(Long id) { return battleRepository.findById(id); }
    public void deleteById(Long id) { battleRepository.deleteById(id); }




    public void applyEvent(Battle battle, Startup startup, String event) {
        Evento eventoEnum;

        // traduz string para enum
        switch (event.toLowerCase()) {
            case "pitch_convincente" -> eventoEnum = Evento.PITCH_CONVINCENTE;
            case "produto_com_bugs" -> eventoEnum = Evento.PRODUTO_COM_BUGS;
            case "boa_tracao" -> eventoEnum = Evento.BOA_TRACAO;
            case "investidor_irritado" -> eventoEnum = Evento.INVESTIDOR_IRRITADO;
            case "fake_news" -> eventoEnum = Evento.FAKE_NEWS;
            default -> throw new IllegalArgumentException("Evento inválido: " + event);
        }

        // Verifica se já foi usado nesta batalha
        if (startup.getId().equals(battle.getStartup1().getId())) {
            if (battle.getEventosStartup1().contains(eventoEnum)) {
                throw new RuntimeException("Este evento já foi aplicado nesta batalha para essa startup.");
            }
            battle.getEventosStartup1().add(eventoEnum);
        } else if (startup.getId().equals(battle.getStartup2().getId())) {
            if (battle.getEventosStartup2().contains(eventoEnum)) {
                throw new RuntimeException("Este evento já foi aplicado nesta batalha para essa startup.");
            }
            battle.getEventosStartup2().add(eventoEnum);
        } else {
            throw new RuntimeException("Startup não pertence a esta batalha.");
        }

        // E agora simplesmente aplica os pontos:
        startup.setScore(startup.getScore() + eventoEnum.getPontos());

        // Atualiza também o histórico da startup
        startup.getEventosAplicados().add(eventoEnum);

        // Salva a batalha atualizada
        battleRepository.save(battle);
    }





    public void createBattlesForRound(Torneio torneio, int round) {
        List<Startup> startups = torneio.getRegisteredStartups();
        Collections.shuffle(startups); // ainda embaralha para não favorecer

        if (startups.size() == 3) {
            Startup a = startups.get(0);
            Startup b = startups.get(1);
            Startup c = startups.get(2);

            // A vs B
            Battle battle1 = Battle.builder()
                    .startup1(a)
                    .startup2(b)
                    .round(round)
                    .torneio(torneio)
                    .build();
            battleRepository.save(battle1);

            // A vs C
            Battle battle2 = Battle.builder()
                    .startup1(a)
                    .startup2(c)
                    .round(round)
                    .torneio(torneio)
                    .build();
            battleRepository.save(battle2);

            // B vs C
            Battle battle3 = Battle.builder()
                    .startup1(b)
                    .startup2(c)
                    .round(round)
                    .torneio(torneio)
                    .build();
            battleRepository.save(battle3);

        } else {
            // Lógica normal de pares de dois em dois
            for (int i = 0; i < startups.size(); i += 2) {
                Startup s1 = startups.get(i);
                Startup s2 = startups.get(i + 1);

                Battle battle = Battle.builder()
                        .startup1(s1)
                        .startup2(s2)
                        .round(round)
                        .torneio(torneio)
                        .build();
                battleRepository.save(battle);
            }
        }
    }


    public List<Battle> getBattlesForRound(Torneio torneio, int round) {
        return battleRepository.findByTorneioAndRound(torneio, round);
    }



    public Startup resolveBattle(Battle battle) {
        Startup s1 = startupService.findById(battle.getStartup1().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Startup 1 não encontrada"));

        Startup s2 = startupService.findById(battle.getStartup2().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Startup 2 não encontrada"));





        Startup winner;
        Startup loser;
        if (s1.getScore() > s2.getScore()) {
            winner = s1;
            loser = s2;
        } else if (s2.getScore() > s1.getScore()) {
            winner = s2;
            loser = s1;
        } else {
            // empate: shark fight
            if (random.nextBoolean()) {
                s1.setScore(s1.getScore() + 2);  // +2 pontos para s1
                startupService.update(s1); // salvar ajuste
                winner = s1;
                loser = s2;
            } else {
                s2.setScore(s2.getScore() + 2);  // +2 pontos para s2
                startupService.update(s2); // salvar ajuste
                winner = s2;
                loser = s1;
            }
        }

        winner.setScore(winner.getScore() + 30);
        startupService.update(winner);
        startupService.update(loser);

        battle.setWinner(winner);
        battleRepository.save(battle);

        // adiciona o perdedor no ranking
        Torneio torneio = torneioService.findById(battle.getTorneio().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Torneio não encontrado"));

        torneio.getRanking().add(loser);
        torneioService.save(torneio);

        return winner;
    }



}














