package com.lucamanfroi.startuprush.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @Size(min = 4, max = 30, message = "O slogan precisa ter no mínimo 2 letras, e no máximo 30.")
    private String slogan;
}
