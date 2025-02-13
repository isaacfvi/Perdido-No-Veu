package com.mygdx.proceduralGeneration;

import java.awt.*;

public enum TileType {
    CHAO(0, "Piso", new Rectangle[]{}),
    PAREDE_VERTICAL(1, "Parede_vertical", new Rectangle[]{new Rectangle(13, 32, 60, 320)}),
    PAREDE_HORIZONTAL(2, "Parede_horizontal", new Rectangle[]{new Rectangle(0 , 17, 320, 100)}),
    PORTA_HORIZONTAL(3, "Porta_horizontal", new Rectangle[]{new Rectangle(0, 17, 60, 60), new Rectangle(25, 17, 60, 60)}),
    PORTA_VERTICAL(4, "Porta_vertical", new Rectangle[]{new Rectangle(13, 32, 60, 200)}),
    QUINA_ESQUERDA_CIMA(5, "Quina_cima_esquerda", new Rectangle[]{new Rectangle(0, 17, 160, 100), new Rectangle(13, 32, 60, 250)}),
    QUINA_DIREITA_CIMA(6, "Quina_cima_direita", new Rectangle[]{new Rectangle(13, 32, 60, 250), new Rectangle(16, 17, 160, 100)}),
    QUINA_ESQUERDA_BAIXO(7, "Quina_baixo_esquerda", new Rectangle[]{new Rectangle(0, 17, 160, 100), new Rectangle(16, 170, 60, 160)}),
    QUINA_DIREITA_BAIXO(8, "Quina_baixo_direita", new Rectangle[]{new Rectangle(13 , 17, 60, 100), new Rectangle(13, 17, 160, 100)}),
    T_BAIXO(9, "T_horizontal_baixo", new Rectangle[]{new Rectangle(0, 17, 32, 100), new Rectangle(13, 16, 60, 160)}),
    T_ESQUERDA(10, "T_vertical_esquerda", new Rectangle[]{new Rectangle(13, 32, 60, 320) , new Rectangle(0, 17, 160, 100)}),
    T_DIREITA(11, "T_vertical_direita", new Rectangle[]{new Rectangle(13, 32, 60, 320) , new Rectangle(13, 17, 160, 100)}),
    T_CIMA(12, "T_horizontal_cima", new Rectangle[]{new Rectangle(0, 17, 320, 100), new Rectangle(13, 32, 60, 250)}),
    CRUZAMENTO(13, "Cruzamento", new Rectangle[]{new Rectangle(13, 32, 60, 320), new Rectangle(0 , 17, 320, 100)}),
    CANTOS(13, "Cantos_mapa", new Rectangle[]{}),
    MOVEL(14, "Movel", new Rectangle[]{new Rectangle(1, 1)}),;

    private final int codigo;
    private final String desc;
    private final Rectangle[] hitboxes;

    TileType(int codigo, String desc, Rectangle[] hitboxes) {
        this.codigo = codigo;
        this.desc = desc;
        this.hitboxes = hitboxes;
    }

    public int getCodigo() {
        return codigo;
    }
    public String getDesc() {
        return desc;
    }

    public Rectangle[] getHitboxes() {
        return hitboxes;
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
