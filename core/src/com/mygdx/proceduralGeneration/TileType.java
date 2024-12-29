package com.mygdx.proceduralGeneration;

public enum TileType {
    CHAO(0),
    PAREDE(1),
    PORTA(2),
    QUINA_ESQUERDA_CIMA(3),
    QUINA_DIREITA_CIMA(4),
    QUINA_ESQUERDA_BAIXO(5),
    QUINA_DIREITA_BAIXO(6),
    T_BAIXO(7),
    T_ESQUERDA(8),
    T_DIREITA(9),
    T_CIMA(10);

    private final int codigo;

    TileType(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
