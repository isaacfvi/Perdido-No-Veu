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
        int seed;
        if (Consts.SEED != -1) seed = Consts.SEED;
        else seed = rand.nextInt();

        System.out.println("Seed: " + seed);
        MapAssembler assembler = new MapAssembler(seed);
        //184565
        this.map = assembler.makeMap(assets);
        this.floors = assembler.getFloors();
        this.entidades = assembler.getEntidades();
        jogador.getHitbox().setPosition(getEntityInitPosition());

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

    public Vector2 getEntityInitPosition(){
        int x, y;

        do{
            x = rand.nextInt(Consts.MAP_SIZE_X);
            y = rand.nextInt(Consts.MAP_SIZE_Y);
            System.out.println("X: "+x+" Y: "+y);
        } while(map[x][y].isCollidable());

        return new Vector2(x * Consts.TILE_SIZE + 12, y * Consts.TILE_SIZE + 12);
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

    public void dispose(){
        for (TileMap[] tilemap : map) {
            for (TileMap tile : tilemap) {
                tile.dispose();
            }
        }
    }

}
