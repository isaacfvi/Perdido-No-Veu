package com.mygdx.core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    private final AssetManager assetManager;

    public Assets() {
        this.assetManager = new AssetManager();

        loadSounds();
        loadTextures();
        assetManager.finishLoading();
    }

    public void loadSounds(){

    }

    public void loadTextures(){
        assetManager.load("MansionTiles.atlas", TextureAtlas.class);
        assetManager.load("Entidades.atlas", TextureAtlas.class);
    }

    public TextureAtlas getAtlas(String name){ return assetManager.get(name + ".atlas", TextureAtlas.class); }

    public Sprite getSpriteFromAtlas(String atlasPath, String resourceName){ return getAtlas(atlasPath).createSprite(resourceName);}

    public TextureRegion getTextureFromAtlas(String atlasPath, String resourceName){ return getAtlas(atlasPath).findRegion(resourceName);}

    public Sprite[][] getSprites(String atlasPath, String resourceName, int cols, int rows){
        TextureRegion region = getTextureFromAtlas(atlasPath, resourceName);

        TextureRegion[][] frames = region.split(region.getRegionWidth() / cols, region.getRegionHeight() / rows);

        Sprite[][] sprites = new Sprite[frames.length][frames[0].length];
        for (int i = 0; i < sprites.length; i++) {
            for (int j = 0; j < sprites[i].length; j++) {
                sprites[i][j] = new Sprite(frames[i][j]);
                sprites[i][j].setSize(32, 32);
            }
        }
        return sprites;
    }

    public Sound getSound(String name){
        return assetManager.get(name + ".wav", Sound.class);
    }

    public boolean update(){
        return assetManager.update();
    }

    public void dispose(){
        assetManager.dispose();
    }
}
