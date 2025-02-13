package com.mygdx.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Collision;
import com.mygdx.proceduralGeneration.GeracaoProcedural;
import com.mygdx.proceduralGeneration.TileMap;
import com.mygdx.proceduralGeneration.TileType;
import com.mygdx.utils.Assets;

import java.awt.*;
import java.util.Random;

public class Mansion {

    private Array<Sprite> floors;
    private Array<Sprite> walls;
    private TextureAtlas atlas;
    private GeracaoProcedural geracao;
    private TileMap[][] map;

    public Mansion(Assets assets) {
        atlas = new TextureAtlas(Gdx.files.internal("MansionTiles.atlas"));

        geracao = new GeracaoProcedural(20, 10, 61646541);
        //61576541
        floors = new Array<>();
        walls = new Array<>();

        generateMap();

    }

    public void generateMap() {
        map = geracao.generate();
        Random rand = new Random(geracao.getSeed());
        float aux;
        Sprite sprite;

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {

                aux = rand.nextFloat();
                aux = aux < 0.1f ? rand.nextInt(2,4) : 1;

                sprite = new Sprite(atlas.createSprite(TileType.CHAO.getDesc() + (int)aux));
                setSprites(sprite, i, j);
                floors.add(sprite);

                if(j == 0 && (i == 0 || i == map.length - 1)) {
                    sprite = new Sprite(atlas.createSprite(TileType.PAREDE_VERTICAL.getDesc()));
                    setSprites(sprite, i, j);
                    walls.add(sprite);
                    inscreverParede(i, j);
                }
                if(i == 0) {
                    sprite = new Sprite(atlas.createSprite(TileType.CANTOS.getDesc()));
                    sprite.rotate(180);
                    setSprites(sprite, i, j);
                    walls.add(sprite);
                    inscreverParede(i, j);

                } else if(i == map.length-1){
                    sprite = new Sprite(atlas.createSprite(TileType.CANTOS.getDesc()));
                    setSprites(sprite, i, j);
                    walls.add(sprite);
                    inscreverParede(i, j);

                }
                if(j == 0){
                    sprite = new Sprite(atlas.createSprite(TileType.CANTOS.getDesc()));
                    sprite.rotate(270);
                    setSprites(sprite, i, j);
                    walls.add(sprite);
                    inscreverParede(i, j);

                    sprite = new Sprite(atlas.createSprite(TileType.PAREDE_VERTICAL.getDesc()));
                    sprite.rotate(90);
                    setSprites(sprite, i, j);
                    walls.add(sprite);
                    inscreverParede(i, j);

                    if(map[i][j].getTileType().getDesc().equals("T_horizontal_cima")){
                        sprite = new Sprite(atlas.createSprite(TileType.PAREDE_VERTICAL.getDesc()));
                        setSprites(sprite, i, j);
                        walls.add(sprite);
                        inscreverParede(i, j);
                    }

                } else if(j == map[0].length-1){
                    sprite = new Sprite(atlas.createSprite(TileType.CANTOS.getDesc()));
                    sprite.rotate(90);
                    setSprites(sprite, i, j);
                    walls.add(sprite);
                    inscreverParede(i, j);

                }

                if(map[i][j].getTileType() != TileType.CHAO && j != 0){

                    if(map[i][j].getTileType().getDesc().equals("Parede_horizontal")){
                        sprite = new Sprite(atlas.createSprite(map[i][j].getTileType().getDesc() + (int)aux));
                    }
                    else{
                        sprite = new Sprite(atlas.createSprite(map[i][j].getTileType().getDesc()));
                    }
                    setSprites(sprite, i, j);
                    walls.add(sprite);
                    inscreverParede(i, j);
                }
            }
        }
    }

    private void inscreverParede(int i, int j){
        Collision collision = Collision.getInstance();
        Rectangle[] hitboxes = map[i][j].getTileType().getHitboxes();

        for(int k = 0; k < hitboxes.length; k++) {
            hitboxes[k].setLocation((int)(hitboxes[k].getX() + map[i][j].getX()), (int)(hitboxes[k].getY() + map[i][j].getY()));
            collision.inscreverParede(hitboxes[k]);
        }

    }

    private void setSprites(Sprite sprite, int i, int j) {
        sprite.setPosition(map[i][j].getX(), map[i][j].getY());
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
    }

}
