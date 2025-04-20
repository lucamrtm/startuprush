package com.lucamanfroi.startuprush.controller;

import com.lucamanfroi.startuprush.domain.Battle;
import com.lucamanfroi.startuprush.domain.Startup;
import com.lucamanfroi.startuprush.domain.Torneio;
import com.lucamanfroi.startuprush.services.BattleService;
import com.lucamanfroi.startuprush.services.StartupService;
import com.lucamanfroi.startuprush.services.TorneioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/battles")
public class BattleController {

    @Autowired
    private BattleService battleService;

    @Autowired
    private StartupService startupService;
    @Autowired
    private TorneioService torneioService;

    // aplica evento em uma startup
    @PostMapping("/{battleId}/apply-event")
    public Startup applyEvent(@PathVariable Long battleId,
                              @RequestParam Long startupId,
                              @RequestParam String event) {
        Battle battle = battleService.findById(battleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Batalha não encontrada"));

        Startup startup = startupService.findById(startupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Startup não encontrada"));

        validateStartupBelongsToBattle(battle, startup);

        battleService.applyEvent(battle, startup, event);
        return startupService.save(startup);
    }

    // apenas valida se a startup pertence a batalha
    private void validateStartupBelongsToBattle(Battle battle, Startup startup) {
        if (!battle.getStartup1().getId().equals(startup.getId()) &&
                !battle.getStartup2().getId().equals(startup.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Startup não pertence a essa batalha");
        }
    }

    // Resolver batalha (escolhe o vencedor)
    @PostMapping("/{battleId}/resolve")
    public Startup resolveBattle(@PathVariable Long battleId) {
        Battle battle = battleService.findById(battleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Batalha não encontrada"));

        return battleService.resolveBattle(battle);
    }

    // Buscar batalhas da rodada atual
    @GetMapping("/torneio/{torneioId}/current-round")
    public List<Battle> getBattlesForCurrentRound(@PathVariable Long torneioId) {
        Torneio torneio = torneioService.findById(torneioId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Torneio não encontrado"));

        return battleService.getBattlesForRound(torneio, torneio.getCurrentRound());
    }

}
