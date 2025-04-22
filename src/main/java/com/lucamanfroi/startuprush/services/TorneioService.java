package com.lucamanfroi.startuprush.services;


import com.lucamanfroi.startuprush.domain.*;
import com.lucamanfroi.startuprush.domain.RankingResponse;
import com.lucamanfroi.startuprush.dto.RankingGeneralResponse;
import com.lucamanfroi.startuprush.repository.BattleRepository;
import com.lucamanfroi.startuprush.repository.StartupRepository;
import com.lucamanfroi.startuprush.repository.TorneioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TorneioService {

    @Autowired
    private TorneioRepository torneioRepository;
    @Autowired
    private StartupRepository startupRepository;
    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    @Lazy
    private BattleService battleService;

    public Torneio save(Torneio torneio) { return torneioRepository.save(torneio); }
    public List<Torneio> findAll() { return torneioRepository.findAll(); }
    public Optional<Torneio> findById(Long id) { return torneioRepository.findById(id); }
    public void deleteById(Long id) { torneioRepository.deleteById(id); }



    // cria um torneio novo
    public Torneio createTorneio(Integer startupQuantity) {
        Torneio torneio = new Torneio();
        torneio.setStartupQuantity(startupQuantity);
        return torneioRepository.save(torneio);
    }

    // registra uma startup para o torneio, caso já não esteja cheio

    public Torneio addStartup(Long torneioId, Startup startup) {
        Torneio torneio = torneioRepository.findById(torneioId).
                orElseThrow(() -> new RuntimeException("Torneio não encontrado"));

        if(torneio.getRegisteredStartups().size() >= torneio.getStartupQuantity()){
            throw new RuntimeException("Torneio já está cheio");
        }
//        // primeiro salva a Startup
//        Startup savedStartup = startupRepository.save(startup);

        // depois adiciona ao torneio
        torneio.getRegisteredStartups().add(startup);
        return torneioRepository.save(torneio);
    }

    public Torneio removeStartup(Long torneioId, Long startupId) {
        Torneio torneio = torneioRepository.findById(torneioId)
                .orElseThrow(() -> new RuntimeException("Torneio não encontrado"));

        if (Boolean.TRUE.equals(torneio.getStarted())) {
            throw new RuntimeException("Não é possível remover startups de um torneio já iniciado.");
        }

        // Remove a startup da lista
        boolean removed = torneio.getRegisteredStartups().removeIf(startup -> startup.getId().equals(startupId));

        if (!removed) {
            throw new RuntimeException("Startup não estava registrada neste torneio");
        }

        return torneioRepository.save(torneio);
    }



    public Torneio startTorneio(Long torneioId) {
        Torneio torneio = torneioRepository.findById(torneioId)
                .orElseThrow(() -> new RuntimeException("Torneio não encontrado"));

        if (torneio.getRegisteredStartups().size() != torneio.getStartupQuantity()) {
            throw new RuntimeException("Número de startups incorreto para começar");
        }

        torneio.setStarted(true);
        torneio.setCurrentRound(1);

        battleService.createBattlesForRound(torneio, 1);

        return torneioRepository.save(torneio);
    }


    public Torneio advanceRound(Long torneioId) {
        Torneio torneio = torneioRepository.findById(torneioId)
                .orElseThrow(() -> new RuntimeException("Torneio não encontrado"));

        List<Battle> currentBattles = battleRepository.findByTorneioAndRound(torneio, torneio.getCurrentRound());

        // verificar se todas batalhas têm vencedor
        boolean allBattlesResolved = currentBattles.stream().allMatch(b -> b.getWinner() != null);
        if (!allBattlesResolved) {
            throw new RuntimeException("Ainda há batalhas sem vencedor nesta rodada.");
        }

        // coletar os vencedores
        List<Startup> winners = new ArrayList<>(currentBattles.stream()
                .map(Battle::getWinner)
                .toList());

        if (torneio.getRegisteredStartups().size() == 3 && torneio.getCurrentRound() ==2) {
            // CASO ESPECIAL: PODIUM
            // todos entram no ranking
            torneio.getRanking().addAll(torneio.getRegisteredStartups());


            List<Startup> startupsFinalistas = new ArrayList<>(torneio.getRegisteredStartups());
            // ordena por score
            startupsFinalistas.sort(Comparator.comparing(Startup::getScore).reversed());

            // campeão é quem ficou em primeiro
            torneio.setChampion(startupsFinalistas.get(0));

            torneio.setStarted(false);
            return torneioRepository.save(torneio);
        }


        if (winners.size() == 1) {
            torneio.setChampion(winners.get(0));
            // adiciona o campeão no ranking
            torneio.getRanking().add(winners.get(0));
            torneio.setStarted(false);


            return torneioRepository.save(torneio);
        }

        // avança para nova rodada
        torneio.setCurrentRound(torneio.getCurrentRound() + 1);
        torneio.setRegisteredStartups(winners);

        battleService.createBattlesForRound(torneio, torneio.getCurrentRound());
        return torneioRepository.save(torneio);
    }

    public Torneio endTorneio(Long torneioId) {
        Torneio torneio = torneioRepository.findById(torneioId).orElseThrow(() -> new RuntimeException("Torneio não encontrado"));

        torneio.setStarted(false);
        return torneioRepository.save(torneio);
    }

    public List<RankingGeneralResponse> getRankingGeral() {
        List<Torneio> torneios = torneioRepository.findAll()
                .stream()
                .filter(torneio -> torneio.getChampion() != null)
                .sorted(Comparator.comparing((Torneio t) -> t.getChampion().getScore()).reversed())
                .toList();

        List<RankingGeneralResponse> response = new ArrayList<>();

        for (Torneio torneio : torneios) {
            response.add(new RankingGeneralResponse(
                    torneio.getId(),
                    torneio.getChampion().getName(),
                    torneio.getChampion().getScore(),
                    torneio.getChampion().getSlogan(),
                    torneio.getStartupQuantity()
            ));
        }

        return response;
    }





    public List<RankingResponse> getRankingDetalhado(Long torneioId) {
        Torneio torneio = torneioRepository.findById(torneioId).orElseThrow(() -> new RuntimeException("Torneio não encontrado"));

        List<Startup> startupsRanked = torneio.getRanking();
        startupsRanked.sort(Comparator.comparing(Startup::getScore).reversed());

        List<RankingResponse> rankingDetalhado = new ArrayList<>();

        for (Startup startup : startupsRanked) {
            Map<Evento, Long> resumoEventos = startup.getEventosAplicados()
                    .stream()
                    .collect(Collectors.groupingBy(evento -> evento, Collectors.counting()));

            rankingDetalhado.add(new RankingResponse(
                    startup.getName(),
                    startup.getScore(),
                    resumoEventos
            ));
        }

        return rankingDetalhado;
    }

}
