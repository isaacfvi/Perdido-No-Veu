package com.mygdx.proceduralGeneration;

public enum TileType {
    CHAO(0, "Piso"),
    PAREDE_VERTICAL(1, "Parede_vertical"),
    PAREDE_HORIZONTAL(2, "Parede_horizontal"),
    PORTA_HORIZONTAL(3, "Porta_horizontal"),
    PORTA_VERTICAL(4, "Porta_vertical"),
    QUINA_ESQUERDA_CIMA(5, "Quina_cima_esquerda"),
    QUINA_DIREITA_CIMA(6, "Quina_cima_direita"),
    QUINA_ESQUERDA_BAIXO(7, "Quina_baixo_esquerda"),
    QUINA_DIREITA_BAIXO(8, "Quina_baixo_direita"),
    T_BAIXO(9, "T_horizontal_baixo"),
    T_ESQUERDA(10, "T_vertical_esquerda"),
    T_DIREITA(11, "T_vertical_direita"),
    T_CIMA(12, "T_horizontal_cima"),
    CRUZAMENTO(13, "Cruzamento"),
    CANTOS(13, "Cantos_mapa"),
    MOVEL(14, "Movel"),;

    private final int codigo;
    private final String desc;

    TileType(int codigo, String desc) {
        this.codigo = codigo;
        this.desc = desc;
    }

    public int getCodigo() {
        return codigo;
    }
    public String getDesc() {
        return desc;
    }

    public static TileType determineType(boolean up, boolean down, boolean left, boolean right, boolean porta) {

        if(up && left && right && down) return TileType.CRUZAMENTO;

        if (up && left && !right && !down) return TileType.QUINA_ESQUERDA_CIMA;
        if (up && right && !left && !down) return TileType.QUINA_DIREITA_CIMA;
        if (down && left && !up && !right) return TileType.QUINA_ESQUERDA_BAIXO;
        if (down && right && !up && !left) return TileType.QUINA_DIREITA_BAIXO;

        if (up && down && left && !right) return TileType.T_ESQUERDA;
        if (up && down && right && !left) return TileType.T_DIREITA;
        if (left && right && up && !down) return TileType.T_CIMA;
        if (left && right && down && !up) return TileType.T_BAIXO;

        if(up && down && !left && !right && porta) return TileType.PORTA_VERTICAL;
        if(!up && !down && left && right && porta) return TileType.PORTA_HORIZONTAL;

        if(up && down && !left && !right) return TileType.PAREDE_VERTICAL;
        if(!up && !down && left && right) return TileType.PAREDE_HORIZONTAL;

        return TileType.CHAO;
    }
}
