package com.lucamanfroi.startuprush.controller;

import com.lucamanfroi.startuprush.domain.RankingResponse;
import com.lucamanfroi.startuprush.domain.Startup;
import com.lucamanfroi.startuprush.domain.Torneio;
import com.lucamanfroi.startuprush.services.TorneioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/torneios")
public class TorneioController {
    @Autowired
    private TorneioService torneioService;


    @GetMapping
    public List<Torneio> listTorneios() {
        return torneioService.findAll();
    }

    @GetMapping("/{id}")
    public Torneio findById(@PathVariable Long id) {
        return torneioService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Torneio não encontrado."));
    }



    @PostMapping("/modalidade")
    public Torneio createTorneio(@RequestParam Integer startupQuantity){
        if (startupQuantity != 4 && startupQuantity != 8){
            throw new IllegalArgumentException("São permitidos somente torneios de 4 ou 8 startups!");
        }
        return torneioService.createTorneio(startupQuantity);
    }

    @Transactional
    @PostMapping("/{torneioId}/startups-registradas")
    public Torneio addStartup(@PathVariable Long torneioId, @RequestBody @Valid Startup startup){
        return torneioService.addStartup(torneioId, startup);
    }



    @PostMapping("/{torneioId}/start")
    public Torneio startTorneio(@PathVariable Long torneioId){
        return torneioService.startTorneio(torneioId);
    }

    @PostMapping("/{torneioId}/end")
    public Torneio endTorneio(@PathVariable Long torneioId){
        return torneioService.endTorneio(torneioId);
    }

    @GetMapping("/{torneioId}/ranking")
    public List<RankingResponse> getRanking(@PathVariable Long torneioId) {
        Torneio torneio = torneioService.findById(torneioId).orElseThrow();

        List<Startup> startupsRanked = torneio.getRanking();

        // ordena as startups por score (do maior para o menor)
        startupsRanked.sort((s1, s2) -> s2.getScore().compareTo(s1.getScore()));


        List<RankingResponse> response = new ArrayList<>();

        int position = 1;
        for (Startup startup : startupsRanked) {
            response.add(new RankingResponse(position, startup.getName(), startup.getScore()));
            position++;
        }


        return response;
    }

}
