package com.lucamanfroi.startuprush.controller;

import com.lucamanfroi.startuprush.domain.Startup;
import com.lucamanfroi.startuprush.domain.Torneio;
import com.lucamanfroi.startuprush.services.TorneioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/torneios")
public class TorneioController {
    @Autowired
    private TorneioService torneioService;

    @PostMapping("/modalidade")
    public Torneio createTorneio(@RequestParam Integer startupQuantity){
        if (startupQuantity != 4 && startupQuantity != 8){
            throw new IllegalArgumentException("São permitidos somente torneios de 4 ou 8 startups!");
        }
        return torneioService.createTorneio(startupQuantity);
    }

    @PostMapping("/{torneioId}/startups-registradas")
    public Torneio addStartup(@PathVariable Long torneioId, @RequestBody Startup startup){
        return torneioService.addStartup(torneioId, startup);
    }



    @PostMapping("/{torneioId}/iniciar")
    public Torneio startTorneio(@PathVariable Long torneioId){
        return torneioService.startTorneio(torneioId);
    }
}
