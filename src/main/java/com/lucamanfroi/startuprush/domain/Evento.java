package com.lucamanfroi.startuprush.domain;

public enum Evento {
    PITCH_CONVINCENTE(6),
    PRODUTO_COM_BUGS(-4),
    BOA_TRACAO(3),
    INVESTIDOR_IRRITADO(-6),
    FAKE_NEWS(-8);

    private final int pontos;

    Evento(int pontos) {
        this.pontos = pontos;
    }

    public int getPontos() {
        return pontos;
    }
}


