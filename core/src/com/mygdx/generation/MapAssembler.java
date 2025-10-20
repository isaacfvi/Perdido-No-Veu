package com.mygdx.generation;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.core.Assets;
import com.mygdx.core.Consts;
import com.mygdx.entities.Entidade;
import com.mygdx.entities.Sapato;
import com.mygdx.entities.Trap;
import com.mygdx.world.Collision;
import com.mygdx.world.TileMap;

import java.util.Random;

public class MapAssembler {

    private GeracaoProcedural geracao;
    private Random rand;
    private Array<TileMap> walls;
    private Array<TileMap> floors;
    private Array<Entidade> entidades;


    public MapAssembler(int seed) {
        this.rand = new Random(seed);
        this.geracao = new GeracaoProcedural(Consts.MAP_SIZE_X, Consts.MAP_SIZE_Y, rand);
        this.walls = new Array<>();
        this.floors = new Array<>();
        this.entidades = new Array<>();
    }

    public TileMap[][] makeMap(Assets asset) {
        int[][] grade = geracao.generate();
        TileMap[][] map = new TileMap[grade.length][grade[0].length];

        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                if (grade[i][j] == 0 || grade[i][j] == 2) { // chao ou porta
                    map[i][j] = new TileMap(asset.getSpriteFromAtlas("MansionTiles", "Piso" + generatePiso()), new Vector2(Consts.TILE_SIZE * i, Consts.TILE_SIZE * j), false);
                    floors.add(map[i][j]);
                } else if(grade[i][j] == 3){
                    map[i][j] = new TileMap(asset.getSpriteFromAtlas("MansionTiles", "Parede"), new Vector2(Consts.TILE_SIZE * i, Consts.TILE_SIZE * j), true);

                }else if(grade[i][j] == 1){

                    if(j > 0 && grade[i][j-1] != 1){
                        map[i][j] = new TileMap(asset.getSpriteFromAtlas("MansionTiles", "ParedeFrontal" + generateParede()), new Vector2(Consts.TILE_SIZE * i, Consts.TILE_SIZE * j), true);

                    } else {
                        map[i][j] = new TileMap(asset.getSpriteFromAtlas("MansionTiles", "Parede"), new Vector2(Consts.TILE_SIZE * i, Consts.TILE_SIZE * j), true);
                    }

                    walls.add(map[i][j]);
                }
            }
        }

        // Geração de traps
        Collision collision = Collision.getInstance();
        Trap trap;
        int x, y;

        for (int i = 0; i < Consts.QUANT_TRAPS; i++) {
            do{
                x = rand.nextInt(Consts.MAP_SIZE_X);
                y = rand.nextInt(Consts.MAP_SIZE_Y);
            } while(map[x][y].isCollidable());

            trap = Trap.create(asset, Consts.TILE_SIZE * x + 16, Consts.TILE_SIZE * y + 16, rand.nextInt(1, 3));
            collision.inscreverEntidade(trap);
            entidades.add(trap);
        }

        // Geração do Sapato
        Sapato sapato;

        do{
            x = rand.nextInt(Consts.MAP_SIZE_X);
            y = rand.nextInt(Consts.MAP_SIZE_Y);
        } while(map[x][y].isCollidable());

        sapato = Sapato.create(asset, Consts.TILE_SIZE * x + 16, Consts.TILE_SIZE * y + 16);
        collision.inscreverEntidade(sapato);
        entidades.add(sapato);

        return map;
    }

    public int generatePiso() {
        float[] probabilidades = {0.333f, 0.333f, 0.333f, 0.001f}; // Soma deve ser 1

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

    public Array<TileMap> getFloors(){ return floors; }
    public Array<Entidade> getEntidades(){ return entidades; }

}
