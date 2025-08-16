package com.mygdx.scenes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.proceduralGeneration.MapAssembler;
import com.mygdx.proceduralGeneration.TileMap;
import com.mygdx.utils.Assets;


public class Mansion {

    private TileMap[][] map;

    public Mansion(Assets assets) {}

    public Array<TileMap> generateMap(Assets assets) {
        MapAssembler assembler = new MapAssembler(358874);

        this.map = assembler.makeMap(assets);

        return assembler.getWalls();
    }

    public void update(){

    }

    public TileMap[][] getMap() { return map; }

    public void draw(SpriteBatch batch){
        for (TileMap[] tileMaps : map) {
            for (TileMap tile : tileMaps) {
                tile.draw(batch);
            }
        }
    }

}
