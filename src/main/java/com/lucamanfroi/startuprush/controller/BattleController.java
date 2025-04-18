package com.lucamanfroi.startuprush.controller;

import com.lucamanfroi.startuprush.domain.Startup;
import com.lucamanfroi.startuprush.domain.Torneio;
import com.lucamanfroi.startuprush.services.BattleService;
import com.lucamanfroi.startuprush.services.StartupService;
import com.lucamanfroi.startuprush.services.TorneioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/torneios/{torneioId}/battles")
public class BattleController {

    @Autowired
    private BattleService battleService;

    @Autowired
    private StartupService startupService;
    @Autowired
    private TorneioService torneioService;

    // aplica evento em uma startup de um torneio
    @PostMapping("/apply-event/{startupId}")
    public Startup applyEvent(@PathVariable Long torneioId, @PathVariable Long startupId, @RequestParam String event) {
        Startup startup = startupService.findById(startupId).orElseThrow();
        battleService.applyEvent(startup, event);
        return startupService.save(startup);
    }

    // resolve uma batalha entre duas startups de um torneio
    @PostMapping("/fight")
    public Startup fight(@PathVariable Long torneioId, @RequestParam Long id1, @RequestParam Long id2) {
        Startup s1 = startupService.findById(id1).orElseThrow();
        Startup s2 = startupService.findById(id2).orElseThrow();
        Torneio torneio = torneioService.findById(torneioId).orElseThrow();

        Startup winner = battleService.resolveBattle(torneio, s1, s2);

        startupService.save(s1);
        startupService.save(s2);

        return winner;
    }
}
