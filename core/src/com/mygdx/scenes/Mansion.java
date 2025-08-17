package com.mygdx.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.Jogador;
import com.mygdx.proceduralGeneration.MapAssembler;
import com.mygdx.proceduralGeneration.TileMap;
import com.mygdx.utils.Assets;
import com.mygdx.utils.Timer;

import java.util.Random;


public class Mansion {

    private TileMap[][] map;
    private Jogador jogador;
    private Array<TileMap> floors;

    private Timer timer = new Timer(5);

    //auxiliares

    private int x, y;
    private Random rand = new Random();

    public Mansion(Jogador jogador) {
        this.jogador = jogador;
    }

    public TileMap[][] generateMap(Assets assets) {
        MapAssembler assembler = new MapAssembler(358874);

        this.map = assembler.makeMap(assets);
        this.floors = assembler.getFloors();

        return map;
    }

    public void update(float delta){
        x = (int)(jogador.getHitbox().x / map[0][0].getHitbox().width);
        y = (int)(jogador.getHitbox().y / map[0][0].getHitbox().height);

        map[x][y].increasePath(1);

        if(timer.checkTimer(delta)){
            for(TileMap floor : floors){
                floor.decreasePath((rand.nextFloat() * 0.166f) - 0.05f);
            }
        }
    }

    public TileMap[][] getMap(Assets assets) {
        if(map != null) return map;
        else return generateMap(assets);
    }

    public void draw(SpriteBatch batch){
        for (TileMap[] tileMaps : map) {
            for (TileMap tile : tileMaps) {
                tile.draw(batch);
            }
        }
    }

}
