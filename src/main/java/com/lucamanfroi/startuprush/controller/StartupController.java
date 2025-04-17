package com.lucamanfroi.startuprush.controller;

import com.lucamanfroi.startuprush.services.StartupService;
import jakarta.validation.Valid;
import com.lucamanfroi.startuprush.domain.Startup;
import com.lucamanfroi.startuprush.services.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/startups")
public class StartupController {

    @Autowired
    private StartupService startupService;



    //salvar nova startup
    @PostMapping
    public Startup createStartup(@RequestBody @Valid Startup startup) {
        return startupService.save(startup);
    }

    //listar todas startups
    @GetMapping
    public List<Startup> listStartups() {
        return startupService.findAll();
    }

    // buscar por Id

    @GetMapping("/{Id}")
    public Startup findById(@PathVariable Long id) {
        return startupService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Startup não encontrada."));
    }

        // deletar por Id
    @DeleteMapping("/{id}")
    public void deleteStartup(@PathVariable Long id) {
        startupService.deleteById(id);
    }
}
