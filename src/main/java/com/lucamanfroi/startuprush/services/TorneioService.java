package com.lucamanfroi.startuprush.services;


import com.lucamanfroi.startuprush.domain.Startup;
import com.lucamanfroi.startuprush.domain.Torneio;
import com.lucamanfroi.startuprush.repository.StartupRepository;
import com.lucamanfroi.startuprush.repository.TorneioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TorneioService {

    @Autowired
    private TorneioRepository torneioRepository;
    @Autowired
    private StartupRepository startupRepository;

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
        Torneio torneio = torneioRepository.findById(torneioId).orElseThrow(() -> new RuntimeException("Torneio não encontrado"));

        if(torneio.getRegisteredStartups().size() >= torneio.getStartupQuantity()){
            throw new RuntimeException("Torneio já está cheio");
        }
        // Primeiro salva a Startup
        Startup savedStartup = startupRepository.save(startup);

        // Depois adiciona ao torneio
        torneio.getRegisteredStartups().add(startup);
        return torneioRepository.save(torneio);
    }

    public Torneio startTorneio(Long torneioId) {
        Torneio torneio = torneioRepository.findById(torneioId).orElseThrow(() -> new RuntimeException("Torneio não encontrado"));

        if (torneio.getRegisteredStartups().size() != torneio.getStartupQuantity()){
            throw new RuntimeException("Não há startups suficientes para iniciar um torneio");
        }
        torneio.setStarted(true);
        return torneioRepository.save(torneio);
    }

    public Torneio endTorneio(Long torneioId) {
        Torneio torneio = torneioRepository.findById(torneioId).orElseThrow(() -> new RuntimeException("Torneio não encontrado"));

        torneio.setStarted(false);
        return torneioRepository.save(torneio);
    }






}
