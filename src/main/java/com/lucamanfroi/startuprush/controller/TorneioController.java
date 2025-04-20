package com.lucamanfroi.startuprush.controller;

import com.lucamanfroi.startuprush.domain.RankingResponse;
import com.lucamanfroi.startuprush.domain.Startup;
import com.lucamanfroi.startuprush.domain.Torneio;
import com.lucamanfroi.startuprush.services.StartupService;
import com.lucamanfroi.startuprush.services.TorneioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/torneios")
public class TorneioController {
    @Autowired
    private TorneioService torneioService;
    @Autowired
    private StartupService startupService;


    @GetMapping
    public List<Torneio> listTorneios() {
        return torneioService.findAll();
    }

    @GetMapping("/{id}")
    public Torneio findById(@PathVariable Long id) {
        return torneioService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Torneio não encontrado."));
    }



    @PostMapping
    public Torneio createTorneio(@RequestParam Integer startupQuantity){
        if (startupQuantity != 4 && startupQuantity != 8){
            throw new IllegalArgumentException("São permitidos somente torneios de 4 ou 8 startups!");
        }
        return torneioService.createTorneio(startupQuantity);
    }


    // adicionar startup no torneio
    @Transactional
    @PostMapping("/{torneioId}/add-startup/{startupId}")
    public Torneio addStartup(@PathVariable Long torneioId, @PathVariable Long startupId) {
        Startup startup = startupService.findById(startupId)
                .orElseThrow(() -> new RuntimeException("Startup não encontrada"));
        return torneioService.addStartup(torneioId, startup);
    }
    // remover startup do torneio
    @DeleteMapping("/{torneioId}/remove-startup/{startupId}")
    public Torneio removeStartup(@PathVariable Long torneioId, @PathVariable Long startupId) {
        return torneioService.removeStartup(torneioId, startupId);
    }



    @PostMapping("/{torneioId}/start")
    public Torneio startTorneio(@PathVariable Long torneioId){
        return torneioService.startTorneio(torneioId);
    }

    // avançar para próxima rodada
    @PostMapping("/{torneioId}/advance")
    public Torneio advanceRound(@PathVariable Long torneioId) {
        return torneioService.advanceRound(torneioId);

    }

    // ranking final
    @GetMapping("/{torneioId}/ranking")
    public List<RankingResponse> getRanking(@PathVariable Long torneioId) {
        return torneioService.getRankingDetalhado(torneioId);
    }


}
