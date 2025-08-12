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
    private MapAssembler assembler;

    public Mansion(Assets assets) {
        generateMap();
    }

    public void generateMap() {
        this.assembler = new MapAssembler(358874);

        this.map = assembler.makeMap();
    }

    public void update(){

    }


    public void draw(SpriteBatch batch){
        for (TileMap[] tileMaps : map) {
            for (TileMap tile : tileMaps) {
                tile.draw(batch);
            }
        }
    }

    public void dispose(){
        assembler.dispose();
    }

}
