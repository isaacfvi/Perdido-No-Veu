package com.mygdx.Collisions;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {
    Rectangle getHitbox();
    void onCollide(Collidable other);
}
