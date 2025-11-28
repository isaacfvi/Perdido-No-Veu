package com.mygdx.ui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.generation.GeracaoProcedural;
import com.mygdx.generation.RoomConnectivityValidator;

import java.util.Random;

public class TccScreen implements Screen {

    private GeracaoProcedural geracao;
    private Batch batch;

    @Override
    public void show() {

        //validate(30,30,100,5);
        geracao = new GeracaoProcedural(30,40,100,5, 546843);
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

    public void validate(int width, int height, int area, int side) {
        int conected = 0;
        int testes = 100000;
        boolean isConnected;

        for(int i = 0; i < testes; i++){
            geracao = new GeracaoProcedural(width, height, area, side, new Random().nextInt());
            geracao.tccGenerate();
            RoomConnectivityValidator validator = new RoomConnectivityValidator(geracao.getGrade());
            isConnected = validator.isAllRoomsConnected();
            if(isConnected){
                conected++;
            }
        }
        System.out.println("Quantidade conectados: " + (float)100*conected/testes + "%");
    }
}
