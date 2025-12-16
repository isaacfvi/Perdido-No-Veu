package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.Camera;
import com.mygdx.entities.Entidade;
import com.mygdx.entities.Jogador;

public class MeuInputProcessor implements InputProcessor {

    private boolean[] moviment;
    private boolean shift, space;

    private Vector2 dir = new Vector2();

    private boolean clickEvent;
    private Vector2 clickPos = new Vector2();

    private Vector2 mouseLoc = new Vector2();
    private char typedChar;

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
        } else if (i == Input.Keys.SHIFT_LEFT) {
            shift = true;
        } else if (i == com.badlogic.gdx.Input.Keys.SPACE) {
            space = true;
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
        } else if (i == Input.Keys.SHIFT_LEFT) {
            shift = false;
        } else if (i == com.badlogic.gdx.Input.Keys.SPACE) {
            space = false;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        typedChar = c;
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            clickEvent = true;
            clickPos.set(screenX, Gdx.graphics.getHeight() - screenY);
            return true;
        }
        return true;
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
        mouseLoc.set(i, Gdx.graphics.getHeight() - i1);
        return true;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public void update(Entidade entity, float delta) {
        float deltaX = (moviment[0] ? -1 : 0) + (moviment[1] ? 1 : 0);
        float deltaY = (moviment[2] ? 1 : 0) + (moviment[3] ? -1 : 0);
        if (deltaX != 0 || deltaY != 0) {
            entity.move(dir.set(deltaX, deltaY), delta);
        }
    }

    public boolean isMoving() {
        // Se qualquer direção de movimento estiver ativa, retorna true
        return moviment[0] || moviment[1] || moviment[2] || moviment[3];
    }

    public Vector2 hasClicked() {
        if (!clickEvent) return null;
        clickEvent = false;
        return clickPos;
    }

    public Vector2 getMouseLoc() {
        return mouseLoc;
    }

    public boolean isRunning(){
        return shift;
    }

    public boolean isSpace() {
        return space;
    }

    public char getTypedChar() {
        char c = typedChar;
        typedChar = 0;
        return c;
    }

    public boolean isKeyPressed(int key) {
        return Gdx.input.isKeyJustPressed(key);
    }

}
