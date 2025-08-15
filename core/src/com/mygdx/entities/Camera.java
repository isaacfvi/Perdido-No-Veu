package com.mygdx.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera{

    private OrthographicCamera camera;
    private Viewport viewport;
    private Vector2 position;

    public Camera(Entidade followingEntidade) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(3*1920, 3*1080, camera);

        position = followingEntidade.getPosition();
        camera.position.set(position.x, position.y, 0);
        camera.zoom = 0.06f;
    }

    public OrthographicCamera getCamera(){
        return camera;
    }

    public void update(float delta) {
        // Como position já é a msm da entidade então não precisa reatualizar ela
        camera.position.set(position.x, position.y, 0);
        camera.update();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
