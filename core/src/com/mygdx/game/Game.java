package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.utils.Assets;
import com.mygdx.utils.Controller;
import com.mygdx.proceduralGeneration.GeracaoProcedural;
import com.mygdx.utils.MeuInputProcessor;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private BitmapFont font;
	private MeuInputProcessor meuInput;
	private Controller controller;
	private Assets assets;
	private float delta;

	private final byte MENU = 0;
	private final byte GAME = 1;
	private byte state = GAME;


	private GeracaoProcedural geracao;

	@Override
	public void create () {
		assets = new Assets();
		while(!assets.update());
		meuInput = new MeuInputProcessor();
		Gdx.input.setInputProcessor(meuInput);
		controller = new Controller(meuInput, assets);
		batch = new SpriteBatch();


		geracao = new GeracaoProcedural(20, 40, 61576541);
		geracao.generate();
		geracao.printGrade();

	}

	@Override
	public void resize(int width, int height) {
		controller.getCamera().resize(width, height);

	}

	@Override
	public void render() {

		delta = Gdx.graphics.getDeltaTime();

		switch (state) {
			case MENU:
				ScreenUtils.clear(Color.DARK_GRAY);
				batch.begin();
				geracao.draw(batch);
				batch.end();

				break;
			case GAME:
				ScreenUtils.clear(Color.DARK_GRAY);
				controller.update(delta);

				batch.setProjectionMatrix(controller.getCamera().getCamera().combined);

				batch.begin();
				controller.draw(batch);
				batch.end();
				break;
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		assets.dispose();
		controller.dispose();
		//geracao.dispose();
	}
}
