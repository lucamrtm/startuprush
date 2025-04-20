package com.lucamanfroi.startuprush.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Torneio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer startupQuantity;

    @OneToMany
    private List<Startup> registeredStartups = new ArrayList<>();


    @OneToMany
    private List<Startup> ranking = new ArrayList<>();


    @ManyToOne
    private Startup champion;

    private Integer currentRound = 0; // começa na rodada 0

    private Boolean started = false;
}
