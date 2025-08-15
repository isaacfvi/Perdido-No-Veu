package com.mygdx.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Camera{

    private OrthographicCamera camera;
    private Viewport viewport;
    private Entidade followingEntidade;

    public Camera(Entidade followingEntidade) {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(3*1920, 3*1080, camera);

        this.followingEntidade = followingEntidade;
        camera.position.set(this.followingEntidade.getPosition().x, this.followingEntidade.getPosition().y, 0);
        camera.zoom = 0.06f;
    }

    public OrthographicCamera getCamera(){
        return camera;
    }

    public void update(float delta) {
        camera.position.set(followingEntidade.getPosition().x, followingEntidade.getPosition().y, 0);
        camera.update();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
