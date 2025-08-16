package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.utils.Assets;
import com.mygdx.utils.Controller;
import com.mygdx.utils.MeuInputProcessor;

public class GameScreen implements Screen {

    private Controller controller;
    private MeuInputProcessor meuInput;
    private Assets assets;
    private SpriteBatch batch;

    @Override
    public void show() {
        assets = new Assets();
        while(!assets.update());
        meuInput = new MeuInputProcessor();
        Gdx.input.setInputProcessor(meuInput);
        controller = new Controller(meuInput, assets);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        controller.update(delta);

        batch.setProjectionMatrix(controller.getCamera().getCamera().combined);

        batch.begin();
        controller.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        controller.getCamera().resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        assets.dispose();
    }
}
