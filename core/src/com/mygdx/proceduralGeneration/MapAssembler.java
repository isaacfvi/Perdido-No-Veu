package com.mygdx.proceduralGeneration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Collision;
import com.mygdx.utils.Assets;

import java.util.Random;

public class MapAssembler {

    private GeracaoProcedural geracao;
    private Random rand;
    private Array<TileMap> walls;


    public MapAssembler(int seed) {
        this.rand = new Random(seed);
        this.geracao = new GeracaoProcedural(40, 40, rand);
        this.walls = new Array<>();
    }

    public TileMap[][] makeMap(Assets asset) {
        int[][] grade = geracao.generate();
        TileMap[][] map = new TileMap[grade.length][grade[0].length];

        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                if (grade[i][j] == 0 || grade[i][j] == 2) { // chao ou porta
                    map[i][j] = new TileMap(asset.getSpriteFromAtlas("MansionTiles", "Piso" + generatePiso()), new Vector2(32 * i, 32 * j), false);

                } else if(grade[i][j] == 1){

                    if(j > 0 && grade[i][j-1] != 1){
                        map[i][j] = new TileMap(asset.getSpriteFromAtlas("MansionTiles", "ParedeFrontal" + generateParede()), new Vector2(32 * i, 32 * j), true);

                    } else {
                        map[i][j] = new TileMap(asset.getSpriteFromAtlas("MansionTiles", "Parede"), new Vector2(32 * i, 32 * j), true);
                    }

                    walls.add(map[i][j]);
                }
            }
        }

        return map;
    }

    public int generatePiso() {
        float[] probabilidades = {0.26f, 0.26f, 0.26f, 0.164f, 0.055f, 0.001f}; // Soma deve ser 1

        float num = rand.nextFloat();
        float acumulado = 0;

        for (int i = 0; i < probabilidades.length; i++) {
            acumulado += probabilidades[i];
            if (num <= acumulado) {
                return i + 1;
            }
        }

        return 1;
    }

    public int generateParede(){
        float[] probabilidades = {0.69f, 0.001f, 0.105f, 0.104f, 0.10f}; // Soma deve ser 1

        float num = rand.nextFloat();
        float acumulado = 0;

        for (int i = 0; i < probabilidades.length; i++) {
            acumulado += probabilidades[i];
            if (num <= acumulado) {
                return i + 1;
            }
        }

        return 1;
    }

    public Array<TileMap> getWalls(){ return walls; }

}
