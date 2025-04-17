package com.lucamanfroi.startuprush.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

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
    @Size(min = 4, max = 20, message = "O nome precisa ter no mínimo 4 letras, e no máximo 20.")
    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ\\\\s]+$", message = "O nome deve conter apenas letras e espaços.")
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 30, message = "O slogan precisa ter no mínimo 2 letras, e no máximo 30.")
    private String slogan;


    @NotNull
    @Min(value = 1900, message = "Ano mínimo é 1900")
    @Max(value = 2025, message = "Ano máximo é 2025.")
    private Integer foundationYear;

    private Integer score = 70; //começa com 70 pontos
}
