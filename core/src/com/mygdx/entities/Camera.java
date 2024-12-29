package com.mygdx.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.utils.MeuInputProcessor;

public class Camera extends Entidade{

    private OrthographicCamera camera;
    private Viewport viewport;

    public Camera(MeuInputProcessor meuInput, int velocidade) {
        super(new Vector2(), velocidade, meuInput);
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(3*1920, 3*1080, camera);

        camera.position.set(super.getPosition().x, super.getPosition().y, 0);
        camera.update();
    }

    public OrthographicCamera getCamera(){
        return camera;
    }

    @Override
    public void update(float delta) {
        super.getMeuInput().update(this, delta);
        camera.position.set(super.getPosition().x, super.getPosition().y, 0);
        camera.update();
    }

    @Override
    public void move(float x, float y) {
        super.addPosition(new Vector2(x * super.getVelocidade(), y * super.getVelocidade()));
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
