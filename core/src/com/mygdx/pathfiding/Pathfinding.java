package com.mygdx.pathfiding;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.Fantasma;
import com.mygdx.entities.Jogador;
import com.mygdx.proceduralGeneration.TileMap;
import com.mygdx.utils.Timer;

import java.util.*;

public class Pathfinding {

    Jogador jogador;
    Fantasma fantasma;
    TileMap[][] map;
    Vector2 dir, aux = new Vector2();
    Timer timer = new Timer(0.2f);

    Node[][] nodes; // grade fixa de nodes

    public Pathfinding(Fantasma fantasma, Jogador jogador, TileMap[][] map, Vector2 dir) {
        this.fantasma = fantasma;
        this.jogador = jogador;
        this.map = map;
        this.dir = dir;

        initNodes(map.length, map[0].length);
    }

    private void initNodes(int width, int height) {
        nodes = new Node[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodes[x][y] = new Node(x, y);
            }
        }
    }

    public void path(float delta){
        perseguir(delta);
    }

    public void perseguir(float delta){
        if(timer.checkTimer(delta)) AStar();
    }

    public void AStar() {
        PriorityQueue<Node> openList = new PriorityQueue<>(
                Comparator.comparingInt(Node::fCost).thenComparingInt(n -> n.hCost)
        );
        Set<Node> closedSet = new HashSet<>();

        fantasma.getHitbox().getCenter(aux);
        Node startNode = nodeFromWorld(aux);

        Node targetNode = nodeFromWorld(new Vector2(
                jogador.getHitbox().x, jogador.getHitbox().y
        ));

        resetNodes();

        startNode.gCost = 0;
        startNode.hCost = heuristic(startNode.x, startNode.y, targetNode.x, targetNode.y);
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current == targetNode) {
                reconstrimirCaminho(current, fantasma.getHitbox().getCenter(aux));
                return;
            }

            closedSet.add(current);

            for (Node neighbor : getNeighbors(current)) {
                if (!isWalkable(neighbor) || closedSet.contains(neighbor)) continue;

                int newGCost = current.gCost + 1;
                if (newGCost < neighbor.gCost) {
                    neighbor.gCost = newGCost;
                    neighbor.hCost = heuristic(neighbor.x, neighbor.y, targetNode.x, targetNode.y);
                    neighbor.parent = current;

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }
    }

    private void resetNodes() {
        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes[0].length; y++) {
                nodes[x][y].gCost = Integer.MAX_VALUE;
                nodes[x][y].hCost = 0;
                nodes[x][y].parent = null;
            }
        }
    }

    private Node nodeFromWorld(Vector2 pos) {
        int x = (int)(pos.x / map[0][0].getHitbox().width);
        int y = (int)(pos.y / map[0][0].getHitbox().height);
        return nodes[x][y];
    }

    private int heuristic(int iniX, int iniY, int finX, int finY) {
        return Math.max(Math.abs(iniX - finX), Math.abs(iniY - finY));
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int nx = node.x + dx[i];
            int ny = node.y + dy[i];

            if (nx >= 0 && nx < nodes.length && ny >= 0 && ny < nodes[0].length) {
                neighbors.add(nodes[nx][ny]);
            }
        }
        return neighbors;
    }

    private boolean isWalkable(Node node) {
        return !map[node.x][node.y].isCollidable();
    }

    private void reconstrimirCaminho(Node targetNode, Vector2 fantasmaPos) {
        List<Node> path = new ArrayList<>();
        Node current = targetNode;

        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        if (path.size() > 1) {
            Node nextStep = path.get(path.size() - 2);

            Vector2 nextPos = new Vector2(
                    nextStep.x * map[0][0].getHitbox().width + map[0][0].getHitbox().width / 2,
                    nextStep.y * map[0][0].getHitbox().height + map[0][0].getHitbox().height / 2
            );

            dir.set(nextPos.sub(fantasmaPos));
        }
    }
}
