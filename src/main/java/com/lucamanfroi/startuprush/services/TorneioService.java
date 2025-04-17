package com.lucamanfroi.startuprush.services;


import com.lucamanfroi.startuprush.domain.Startup;
import com.lucamanfroi.startuprush.domain.Torneio;
import com.lucamanfroi.startuprush.repository.TorneioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TorneioService {

    @Autowired
    private TorneioRepository torneioRepository;


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




}
