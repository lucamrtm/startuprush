package com.lucamanfroi.startuprush.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BattleResolutionRequest {
    private Map<Long, List<String>> eventosAplicados; // startupId -> lista de eventos aplicados
}
