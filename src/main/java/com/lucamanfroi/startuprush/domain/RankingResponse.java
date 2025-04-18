package com.lucamanfroi.startuprush.domain;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RankingResponse {
    private Integer position;
    private String startupName;
    private Integer score;
}
