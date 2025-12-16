package com.mygdx.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.ui.GameScreen;
import com.mygdx.ui.Menu;
import com.mygdx.ui.ScreenNavigator;


public class GameStart extends Game implements ScreenNavigator {

	@Override
	public void create () {
		this.setScreen(new Menu(this));
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

	@Override
	public void goToMenu() {
		setScreen(new Menu(this));
	}

	@Override
	public void goToGame() {
		setScreen(new GameScreen(this));
	}

	@Override
	public void exit() {
		Gdx.app.exit();
	}
}
