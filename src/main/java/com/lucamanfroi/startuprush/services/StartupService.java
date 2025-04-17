package com.lucamanfroi.startuprush.services;

import com.lucamanfroi.startuprush.domain.Startup;
import com.lucamanfroi.startuprush.repository.StartupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StartupService {
    @Autowired
    private StartupRepository startupRepository;

    public Startup save(Startup startup) { return startupRepository.save(startup); }
    public List<Startup> findAll() { return startupRepository.findAll(); }
    public Optional<Startup> findById(Long id) { return startupRepository.findById(id); }
    public void deleteById(Long id) { startupRepository.deleteById(id); }
}

