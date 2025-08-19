package com.mygdx.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.Array;
import com.mygdx.core.Consts;
import com.mygdx.entities.Jogador;
import com.mygdx.generation.MapAssembler;
import com.mygdx.core.Assets;
import com.mygdx.utils.Timer;

import java.util.Random;


public class Mansion {

    private TileMap[][] map;
    private Jogador jogador;
    private Array<TileMap> floors;

    private Timer timer = new Timer(5);

    private Random rand = new Random();

    public Mansion(Jogador jogador) {
        this.jogador = jogador;
    }

    public TileMap[][] generateMap(Assets assets) {
        MapAssembler assembler = new MapAssembler(Consts.SEED);

        this.map = assembler.makeMap(assets);
        this.floors = assembler.getFloors();

        return map;
    }

    public void update(float delta){
        TileMap tile = Collision.getInstance().getTile(jogador.getHitbox());
        tile.increasePath(1);

        if(timer.checkTimer(delta)){
            for(TileMap floor : floors){
                floor.decreasePath(0.166f + (rand.nextFloat() * 0.1f - 0.05f));
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
