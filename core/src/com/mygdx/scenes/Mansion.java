package com.mygdx.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.mygdx.proceduralGeneration.GeracaoProcedural;
import com.mygdx.proceduralGeneration.TileMap;
import com.mygdx.proceduralGeneration.TileType;
import com.mygdx.utils.Assets;

import java.util.Random;

public class Mansion {

    private Texture texture;
    private Array<Sprite> floors;
    private Array<Sprite> walls;
    private TextureAtlas atlas;
    private GeracaoProcedural geracao;
    private TileMap[][] map;

    public Mansion(Assets assets) {
        atlas = new TextureAtlas(Gdx.files.internal("MansionTiles.atlas"));

        geracao = new GeracaoProcedural(20, 10, 61576541);
        floors = new Array<>();
        walls = new Array<>();

        generateMap();

    }

    public void generateMap() {
        map = geracao.generate();
        final int scl = 10;
        Random rand = new Random(geracao.getSeed());
        float aux;
        Sprite sprite;

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {

                aux = rand.nextFloat();
                aux = aux < 0.1f ? rand.nextInt(2,4) : 1;

                sprite = new Sprite(atlas.createSprite(TileType.CHAO.getDesc() + (int)aux));
                sprite.setScale(scl, scl);
                sprite.setPosition(scl*map[i][j].getX(),scl* map[i][j].getY());
                floors.add(sprite);

                if(map[i][j].getTileType() != TileType.CHAO){

                    if(map[i][j].getTileType().getDesc().equals("Parede_horizontal")){
                        sprite = new Sprite(atlas.createSprite(map[i][j].getTileType().getDesc() + (int)aux));
                    }
                    else{
                        sprite = new Sprite(atlas.createSprite(map[i][j].getTileType().getDesc()));
                    }
                    sprite.setScale(scl, scl);
                    sprite.setPosition(scl * map[i][j].getX(), scl * map[i][j].getY());
                    walls.add(sprite);

                }
            }
        }
    }

    public void update(){
    }

    public void drawFloor(SpriteBatch batch){
        for(int i = 0; i < floors.size; i++) {
            floors.get(i).draw(batch);
        }
    }

    public void drawWalls(SpriteBatch batch){
        for(int i = 0; i < walls.size; i++) {
            walls.get(i).draw(batch);
        }
    }

    public void dispose(){
        geracao.printGrade();
        texture.dispose();
    }

}
