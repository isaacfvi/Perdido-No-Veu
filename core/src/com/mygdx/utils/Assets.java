package com.mygdx.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

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
        assetManager.load("Piso.png", Texture.class);
        assetManager.load("Player.png", Texture.class);
    }

    public Texture getTexture(String name){
        return assetManager.get(name + ".png", Texture.class);
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
