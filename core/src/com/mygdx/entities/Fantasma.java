package com.mygdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.core.Consts;
import com.mygdx.pathfiding.PathRender;
import com.mygdx.utils.Timer;
import com.mygdx.world.TileMap;
import com.mygdx.utils.Animation;
import com.mygdx.core.Assets;
import com.mygdx.pathfiding.Pathfinding;

import java.util.Locale;
import java.util.Random;

public class Fantasma extends Entidade {

    private Vector2 dir = new Vector2();
    private Pathfinding pathfinding;
    Jogador jogador;

    private int numRays = 32;
    private Ray[] rays = new Ray[numRays];
    private Timer rayTimer = new Timer(0.4f);
    private Timer waitTimer = new Timer(2f);
    private Timer fugaTimer = new Timer(20f);

    private Vector2 target = new Vector2();
    private TileMap bestTrail;
    private Random rand = new Random();
    private FantasmaState state;

    private final int run = 150;
    private final int walk = 50;

    private final PathRender pathRenderer = new PathRender();

    public static Fantasma create(Assets assets, int velocidade, float iniX, float iniY, Jogador jogador, TileMap[][] map) {
        Animation anim = new Animation(assets, "Fantasma", 6, 2);
        Rectangle hitbox = anim.getBounds();
        hitbox.setCenter(iniX, iniY);
        hitbox.setSize(hitbox.width - 20, hitbox.height - 12);
        return new Fantasma(hitbox, velocidade, anim, jogador, map);
    }

    public Fantasma(Rectangle hitbox, int velocidade, Animation anim, Jogador jogador, TileMap[][] map) {
        super(hitbox, velocidade, anim);

        float angleStep = (float)360/numRays;
        for(int i = 0; i < numRays; i++) {
            rays[i] = new Ray(new Rectangle(0, 0, 2, 2), angleStep * i, 20);
        }

        getNewTarget();

        this.jogador = jogador;
        this.state = FantasmaState.PATRULHA;
        pathfinding = new Pathfinding(this, map, dir);
    }

    public void update(float delta){
        super.update(delta);

        perception(delta);
        plan(delta);
        act(delta);

        if(Consts.DEBUG) pathRenderer.addPoint(getPosition());
    }

    public void perception(float delta){
        if(rayTimer.checkTimer(delta) && state != FantasmaState.FUGA) lookUp();
    }

    public void plan(float delta){
        switch (state) {
            case PERSEGUE:
                target.set(jogador.getPosition());
                break;
            case PATRULHA:
                if(bestTrail != null) bestTrail.getHitbox().getCenter(target);
                else if(getPosition().dst(target) < 50) getNewTarget();
                break;
            case INVESTIGA:
                if(getPosition().dst(target) < 20) state = FantasmaState.PARADO;
                break;
            case PARADO:
                if(waitTimer.checkTimer(delta)){
                    state = FantasmaState.PATRULHA;
                    setVelocidade(walk);
                }
                break;
            case FUGA:
                if(fugaTimer.checkTimer(delta) || getPosition().dst(target) < 50){
                    state = FantasmaState.PATRULHA;
                    setVelocidade(walk);
                }
                break;
        }

    }

    public void act(float delta){
        if(state != FantasmaState.PARADO) {
            pathfinding.goTo(delta, target);
            move(dir, delta);
        }
    }

    public void getNewTarget(){
        target.set(
                rand.nextInt(Consts.MAP_SIZE_X) * Consts.TILE_SIZE,
                rand.nextInt(Consts.MAP_SIZE_Y) * Consts.TILE_SIZE
        );
    }

    public void setTarget(Vector2 target) {
        if(target != null) {
            state = FantasmaState.INVESTIGA;
            setVelocidade(run);
            this.target.set(target);
        }
    }

    public void lookUp(){
        TileMap bestTrail = null;

        for(Ray ray : rays){
            ray.startMovement(getPosition());

            if(ray.isDetectedPlayer()){
                if(state != FantasmaState.PERSEGUE) {
                    state = FantasmaState.PERSEGUE;
                    setVelocidade(run);
                    return;
                }
            }

            if(ray.getBestTile() != null){
                if(bestTrail == null || ray.getBestTile().getPlayerPath() > bestTrail.getPlayerPath()){
                    bestTrail = ray.getBestTile();
                }
            }
        }

        if(bestTrail != null && bestTrail.getPlayerPath() > 0) {
            this.bestTrail = bestTrail;
        }
        else this.bestTrail = null;
    }

    public boolean isRunning(){
        return state == FantasmaState.FUGA;
    }

    public void onCollide(Entidade other) {
        if(other instanceof Salt){
            state = FantasmaState.FUGA;
            do{
                getNewTarget();
            }while(target.dst(jogador.getPosition()) < Consts.TILE_SIZE * 20);
            setVelocidade(run);
        }
    }

    // Debug

    public void drawPath(Matrix4 projectionMatrix) {
        pathRenderer.draw(projectionMatrix);
    }

    public void drawVision(ShapeRenderer shapeRenderer) {
        if (rays == null) return;

        shapeRenderer.setColor(0, 1, 1, 0.3f); // CIANO com transparência

        Vector2 ghostPos = getPosition();

        for (Ray ray : rays) {
            if (ray != null) {
                Vector2 direction = new Vector2(1, 0)
                        .rotateDeg(ray.getAngle() + 90)
                        .scl(ray.getSteps());

                Vector2 end = new Vector2(ghostPos).add(direction);

                shapeRenderer.line(ghostPos, end);

                if (ray.isDetectedPlayer()) {
                    shapeRenderer.setColor(1, 0, 0, 1);
                    shapeRenderer.line(ghostPos, end);
                    shapeRenderer.setColor(0, 1, 1, 0.3f);
                }
            }
        }
    }
}

enum FantasmaState {
    PATRULHA,
    PERSEGUE,
    INVESTIGA,
    PARADO,
    FUGA
}
