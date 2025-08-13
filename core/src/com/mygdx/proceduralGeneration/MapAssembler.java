package com.mygdx.proceduralGeneration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Collision;

import java.util.Random;

public class MapAssembler {

    private GeracaoProcedural geracao;
    private Random rand;
    private TextureAtlas atlas;
    private Array<TileMap> walls;


    public MapAssembler(int seed){
        this.rand = new Random(seed);
        this.geracao = new GeracaoProcedural(40, 40, rand);
        this.atlas = new TextureAtlas(Gdx.files.internal("MansionTiles.atlas"));
        this.walls = new Array<>();
    }

    public TileMap[][] makeMap(){
        int[][] grade = geracao.generate();
        TileMap[][] map = new TileMap[grade.length][grade[0].length];

        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                if (grade[i][j] == 0 || grade[i][j] == 2) { // chao ou porta
                    map[i][j] = new TileMap(atlas.createSprite("Piso" + rand.nextInt(1, 4)), new Vector2(32 * i, 32 * j), false);

                } else if (grade[i][j] == 1) { // parede
                    map[i][j] = new TileMap(atlas.createSprite("Parede"), new Vector2(32 * i, 32 * j), true);
                    walls.add(map[i][j]);
                }
            }
        }

        return map;
    }

    public Array<TileMap> getWalls(){ return walls; }

    public void dispose(){atlas.dispose();}

}
