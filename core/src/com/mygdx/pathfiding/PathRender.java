package com.mygdx.pathfiding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import java.util.LinkedList;
import java.util.List;

public class PathRender {

    private final List<Vector2> pathPoints = new LinkedList<>();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final int maxPoints = 200; // limite opcional para não pesar

    public void addPoint(Vector2 position) {
        // adiciona cópia da posição atual
        pathPoints.add(new Vector2(position));
        if (pathPoints.size() > maxPoints) pathPoints.remove(0);
    }

    public void draw(Matrix4 projectionMatrix) {
        if (pathPoints.size() < 2) return;

        shapeRenderer.setProjectionMatrix(projectionMatrix);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 1, 0, 0.8f);

        for (int i = 1; i < pathPoints.size(); i++) {
            Vector2 p1 = pathPoints.get(i - 1);
            Vector2 p2 = pathPoints.get(i);
            shapeRenderer.line(p1.x, p1.y, p2.x, p2.y);
        }

        shapeRenderer.end();
    }

    public void clear() {
        pathPoints.clear();
    }
}
