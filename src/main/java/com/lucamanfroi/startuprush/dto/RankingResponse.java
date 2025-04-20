package com.lucamanfroi.startuprush.domain;

        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponse {
    private String startupName;
    private Integer score;
    private Map<Evento, Long> eventosResumo;
}

