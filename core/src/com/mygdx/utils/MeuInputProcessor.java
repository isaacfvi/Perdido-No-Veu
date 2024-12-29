package com.mygdx.utils;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.entities.Camera;
import com.mygdx.entities.Entidade;
import com.mygdx.entities.Jogador;

public class MeuInputProcessor implements InputProcessor {

    private boolean[] moviment;

    public MeuInputProcessor() {

        this.moviment = new boolean[4]; // left right up down
    }

    @Override
    public boolean keyDown(int i) {
        if(i == com.badlogic.gdx.Input.Keys.A){
            moviment[0] = true;
        } else if (i == com.badlogic.gdx.Input.Keys.D) {
            moviment[1] = true;
        }else if (i == com.badlogic.gdx.Input.Keys.W) {
            moviment[2] = true;
        }else if (i == com.badlogic.gdx.Input.Keys.S) {
            moviment[3] = true;
        }
        return true;
    }

    @Override
    public boolean keyUp(int i) {

        if(i == com.badlogic.gdx.Input.Keys.A){
            moviment[0] = false;
        } else if (i == com.badlogic.gdx.Input.Keys.D) {
            moviment[1] = false;
        }else if (i == com.badlogic.gdx.Input.Keys.W) {
            moviment[2] = false;
        }else if (i == com.badlogic.gdx.Input.Keys.S) {
            moviment[3] = false;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public void update(Entidade entity, float delta) {
        float deltaX = (moviment[0] ? -delta : 0) + (moviment[1] ? delta : 0);
        float deltaY = (moviment[2] ? delta : 0) + (moviment[3] ? -delta : 0);
        if (deltaX != 0 || deltaY != 0) {
            entity.move(deltaX, deltaY);
        }
    }
}
