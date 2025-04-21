package com.lucamanfroi.startuprush.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingGeneralResponse {
    private Long torneioId;
    private String championName;
    private Integer championScore;
    private String championSlogan;
    private Integer startupQuantity;
}
