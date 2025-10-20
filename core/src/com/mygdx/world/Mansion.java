package com.mygdx.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.core.Consts;
import com.mygdx.entities.Entidade;
import com.mygdx.entities.Jogador;
import com.mygdx.entities.Trap;
import com.mygdx.generation.MapAssembler;
import com.mygdx.core.Assets;
import com.mygdx.utils.Timer;

import java.util.Random;


public class Mansion {

    private TileMap[][] map;
    private Jogador jogador;
    private Array<TileMap> floors;
    private Array<Entidade> entidades;

    private Timer timer = new Timer(1f);

    private Random rand = new Random();

    public Mansion(Jogador jogador) {
        this.jogador = jogador;
    }

    public TileMap[][] generateMap(Assets assets) {
        MapAssembler assembler = new MapAssembler(Consts.SEED);

        this.map = assembler.makeMap(assets);
        this.floors = assembler.getFloors();
        this.entidades = assembler.getEntidades();

        for (Entidade entidade : entidades) {
            entidade.update(0);
        }

        return map;
    }

    public void update(float delta){
        TileMap tile = Collision.getInstance().getTile(jogador.getHitbox());
        tile.increasePath(0.166f + (rand.nextFloat() * 0.1f - 0.05f));

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

        for (Entidade entidade : entidades) {
            entidade.draw(batch);
        }
    }

    public Vector2 checkTrap(){
        Trap trap;
        for(Entidade entidade : entidades){
            if(entidade instanceof Trap){
                trap = (Trap)entidade;
                if(trap.isDetectPlayer()){
                    trap.desactive();
                    return trap.getPosition();
                }
            }
        }
        return null;
    }

}
