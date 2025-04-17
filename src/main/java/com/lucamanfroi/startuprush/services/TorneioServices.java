package com.lucamanfroi.startuprush.services;


import com.lucamanfroi.startuprush.domain.Torneio;
import com.lucamanfroi.startuprush.repository.TorneioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TorneioServices {

    @Autowired
    private TorneioRepository torneioRepository;


    // Cria um torneio novo
    public Torneio criarTorneio(Integer quantidadeStartups) {
        Torneio torneio = new Torneio();
        torneio.setQuantidadeStartups(quantidadeStartups);
        return torneioRepository.save(torneio);

    }

}
