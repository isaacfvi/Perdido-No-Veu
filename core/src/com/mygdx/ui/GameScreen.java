package com.mygdx.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.core.Assets;
import com.mygdx.core.Consts;
import com.mygdx.utils.Controller;
import com.mygdx.utils.MeuInputProcessor;
import com.mygdx.utils.Timer;
import com.sun.org.apache.bcel.internal.Const;

public class GameScreen implements Screen {

    private Controller controller;
    private MeuInputProcessor meuInput;
    private Assets assets;
    private SpriteBatch batch;

    private ScreenNavigator navigator;

    public GameScreen(ScreenNavigator navigator) {
        this.navigator = navigator;
    }

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

        if(Consts.DEBUG){
            controller.drawDebug();
            controller.drawPaths(controller.getCamera().getCamera().combined);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            navigator.goToMenu();
        }
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
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assets.dispose();
        controller.dispose();
    }
}
