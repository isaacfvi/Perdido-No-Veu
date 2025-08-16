package com.mygdx.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    private final AssetManager assetManager;

    public Assets() {
        this.assetManager = new AssetManager();

        loadSounds();
        loadTextures();
    }

    public void loadSounds(){

    }

    public void loadTextures(){
        assetManager.load("Fantasma.png", Texture.class);
        assetManager.load("MansionTiles.png", Texture.class);
        assetManager.load("Player.png", Texture.class);
        assetManager.load("MansionTiles.atlas", TextureAtlas.class);
        assetManager.finishLoading();
    }

    public Texture getTexture(String name){
        return assetManager.get(name + ".png", Texture.class);
    }

    public TextureAtlas getAtlas(){ return assetManager.get("MansionTiles.atlas", TextureAtlas.class); }

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
