package com.mygdx.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.proceduralGeneration.GeracaoProcedural;

public class TccScreen implements Screen {

    private GeracaoProcedural geracao;
    private Batch batch;

    @Override
    public void show() {
        geracao = new GeracaoProcedural(30, 40, 546843);
        geracao.tccGenerate();
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);

        batch.begin();
        geracao.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        geracao.dispose();
    }
}
