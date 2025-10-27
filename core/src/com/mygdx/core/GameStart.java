package com.mygdx.core;

import com.badlogic.gdx.Game;
import com.mygdx.ui.GameScreen;
import com.mygdx.ui.TccScreen;


public class GameStart extends Game {

	@Override
	public void create () {
		this.setScreen(new GameScreen());
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
