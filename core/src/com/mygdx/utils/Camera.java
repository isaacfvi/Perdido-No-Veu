package com.mygdx.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera {

    private MeuInputProcessor meuInput;
    private OrthographicCamera camera;
    private int velocidade = 500;
    private Viewport viewport;
    private Vector2 position;

    public Camera(MeuInputProcessor meuInput) {
        this.camera = new OrthographicCamera();
        this.meuInput = meuInput;
        this.viewport = new FitViewport(3*1920, 3*1080, camera);
        this.position = new Vector2();
        camera.position.set(position.x, position.y, 0);
        camera.update();
    }

    public void move(float x, float y) {
        position.x += x * velocidade;
        position.y += y * velocidade;
    }

    public OrthographicCamera getCamera(){
        return camera;
    }

    public void update(float delta) {
        meuInput.update(this, delta);
        camera.position.set(position.x, position.y, 0);
        camera.update();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
