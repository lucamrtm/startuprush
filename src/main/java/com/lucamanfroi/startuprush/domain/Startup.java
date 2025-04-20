package com.lucamanfroi.startuprush.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Startup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 4, max = 30, message = "O nome precisa ter no mínimo 4 letras, e no máximo 30.")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "O nome deve conter apenas letras e espaços.")

    private String name;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50, message = "O slogan precisa ter no mínimo 2 letras, e no máximo 50.")
    private String slogan;


    @NotNull
    @Min(value = 1900, message = "Ano mínimo é 1900")
    @Max(value = 2025, message = "Ano máximo é 2025.")
    private Integer foundationYear;

    private Integer score = 70; //começa com 70 pontos

    @ElementCollection
    private List<Evento> eventosAplicados = new ArrayList<>();
}
