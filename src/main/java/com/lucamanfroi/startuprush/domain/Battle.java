package com.lucamanfroi.startuprush.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Startup startup1;

    @ManyToOne
    private Startup startup2;

    @ManyToOne
    private Torneio torneio;


    @ManyToOne
    private Startup winner; // comeca nulo

    private Integer round; // Rodada atual

    @ElementCollection
    private Set<Evento> eventosStartup1 = new HashSet<>();

    @ElementCollection
    private Set<Evento> eventosStartup2 = new HashSet<>();
}
