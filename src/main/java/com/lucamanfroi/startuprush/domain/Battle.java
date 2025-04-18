package com.lucamanfroi.startuprush.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private Startup winner; // quem vencer a batalha
}
