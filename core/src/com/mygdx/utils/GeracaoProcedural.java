package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.scenes.Room;


import java.util.*;

public class GeracaoProcedural {

    private int[][] grade;
    private final Random rand;
    List<Room> rooms;
    private Pixmap pixmap;
    private Texture texture;
    private final boolean saveImage = false;
    Consts cs;


    private final int minRoomArea = 100;
    private final int minRoomSide = 5;
    private final int maxCorridorSideAdjascency = 0;

    public GeracaoProcedural(int width, int height, int seed) {
        rand = new Random();
        rand.setSeed(8324496);
        // melhor seed ever: 8324493
        grade = new int[width][height];
        pixmap = new Pixmap(height, width, Pixmap.Format.RGBA8888);
        rooms = new ArrayList<>();

        generate();

        texture = generatePixmap();

        if(saveImage) {
            savePixmap("v0.6.3.png");
        }

    }

    public void generate(){
        initMap();
        generate(0, 0, grade.length - 1, grade[0].length - 1, rand.nextBoolean());
        createEntrances();
        posProcess();

    }

    public void initMap() {
        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                if (i == 0 || i == grade.length - 1 || j == 0 || j == grade[i].length - 1) {
                    grade[i][j] = 1;
                }
            }
        }
    }

    public void generate(int startX, int startY, int endX, int endY, boolean bool) {
        if ((endX - startX) * (endY - startY) < minRoomArea || (endX - startX < minRoomSide) || (endY - startY < minRoomSide)) {
            rooms.add(new Room(startX, startY, endX, endY, minRoomSide));

        }else if (bool) {
            split(startX, endX, startY, endY, true, false);
        } else {
            split(startX, endX, startY, endY, false, true);
        }
    }

    private void split(int startX, int endX, int startY, int endY, boolean isVertical, boolean bool) {
        int splitPosition;
        if (isVertical) {
            splitPosition = rand.nextInt(startX + minRoomSide/2, endX - minRoomSide/2);

            for (int i = startY; i < endY; i++) {
                grade[splitPosition][i] = 1;
            }
            generate(startX, startY, splitPosition, endY, bool);
            generate(splitPosition, startY, endX, endY, bool);
        } else {
            splitPosition = rand.nextInt(startY + minRoomSide/2, endY - minRoomSide/2);

            for (int i = startX; i < endX; i++) {
                grade[i][splitPosition] = 1;
            }
            generate(startX, startY, endX, splitPosition, bool);
            generate(startX, splitPosition, endX, endY, bool);
        }

    }

    private void posProcess() {
        fixUnconectedRooms();
        openCorridors();
        fixCorners();

    }

    private void fixUnconectedRooms(){
        for(Room room : rooms){
            if(!room.isConected(grade)){

                for(Room other : rooms){
                    if(room == other)continue;
                    if(room.isAdjascent(other)){
                        criarEntrada(room, other);
                        break;
                    }
                }
            }
        }
    }

    private void openCorridors() {
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            if (room.getRoomType() != Consts.CORREDOR) continue;

            for (int j = i + 1; j < rooms.size(); j++) {
                Room other = rooms.get(j);
                if (other.getRoomType() != Consts.CORREDOR) continue;

                conectRooms(room, other);
            }
        }
    }


    private void conectRooms(Room room, Room other) {
        int start, end;

        // Conexão Vertical (mesmo eixo X)
        if (room.getEndX() == other.getStartX() || room.getStartX() == other.getEndX()) {
            start = Math.max(room.getStartY(), other.getStartY());
            end = Math.min(room.getEndY(), other.getEndY());

            if (end - start >= maxCorridorSideAdjascency) {
                // Remove parede vertical
                for (int k = start + 1; k < end; k++) {
                    grade[room.getEndX() == other.getStartX() ? room.getEndX() : room.getStartX()][k] = 0;
                }
            }
        }

        // Conexão Horizontal (mesmo eixo Y)
        if (room.getEndY() == other.getStartY() || room.getStartY() == other.getEndY()) {
            start = Math.max(room.getStartX(), other.getStartX());
            end = Math.min(room.getEndX(), other.getEndX());

            if (end - start >= maxCorridorSideAdjascency) {
                // Remove parede horizontal
                for (int k = start + 1; k < end; k++) {
                    grade[k][room.getEndY() == other.getStartY() ? room.getEndY() : room.getStartY()] = 0;
                }
            }
        }
    }

    private void fixCorners(){
        int[] neirbors = new int[4];

        for(int i = 1; i < grade.length -1; i++){
            for(int j = 1; j < grade[i].length -1; j++){
                if(grade[i][j] == 0) continue;

                neirbors[0] = grade[i-1][j];
                neirbors[1] = grade[i+1][j];
                neirbors[2] = grade[i][j-1];
                neirbors[3] = grade[i][j+1];

                if(neirbors[0] == 0 && neirbors[1] == 0 && neirbors[2] == 0 && neirbors[3] == 0) grade[i][j] = 0;

            }
        }
    }

    public void ramdomWalk(){

        int x = rand.nextInt( 2, grade[0].length - 2);
        int y = rand.nextInt(2, grade.length - 2);
        int direcao, anterior;

        boolean[][] visitados = new boolean[grade.length][grade[0].length];

        for(int i = 0; i < 15; i++){
            direcao = rand.nextInt(4);
            anterior = grade[y][x];
            switch (direcao) {
                case 0:
                    if(x < grade[0].length - 2 && !visitados[y][x + 1]) {
                        x++;
                    }
                    break;
                case 1:
                    if(x > 1 && !visitados[y][x - 1]){;
                        x--;
                    }
                    break;
                case 2:
                    if(y < grade.length - 2 && !visitados[y + 1][x]) {
                        y++;
                    }
                    break;
                case 3:
                    if(y > 1 && !visitados[y - 1][x]){
                        y--;
                    }
                    break;
            }
            visitados[y][x] = true;
            if(grade[y][x] == 1 && !isQuina(y, x) && anterior != 1){
                grade[y][x] = 3;
            }
        }
    }

    private boolean isQuina(int y, int x) {
        boolean quinaCima = ((grade[y-1][x] == 1 || grade[y-1][x] == 3) && (grade[y][x-1] == 1 || grade[y][x-1] == 3) && (grade[y][x+1] == 1 || grade[y][x+1] == 3));
        boolean quinaBaixo = ((grade[y+1][x] == 1 || grade[y+1][x] == 3) && (grade[y][x-1] == 1 || grade[y][x-1] == 3) && (grade[y][x+1] == 1 || grade[y][x+1] == 3));
        boolean quinaEsquerda = ((grade[y][x-1] == 1 || grade[y][x-1] == 3) && (grade[y-1][x] == 1 || grade[y-1][x] == 3) && (grade[y+1][x] == 1 || grade[y+1][x] == 3));
        boolean quinaDireita = ((grade[y][x+1] == 1 || grade[y][x+1] == 3) && (grade[y-1][x] == 1 || grade[y-1][x] == 3) && (grade[y+1][x] == 1 || grade[y+1][x] == 3));

        return quinaCima || quinaBaixo || quinaEsquerda || quinaDireita;
    }

    public void createEntrances() {
        for (Room room : rooms) {
            for (Room other : rooms) {
                if (room == other || other.getRoomType() != Consts.CORREDOR) continue;
                criarEntrada(room, other);
            }
        }
    }

    private void criarEntrada(Room room, Room other) {
        int start, end, mid;

        // Conexão horizontal
        if (room.getEndX() == other.getStartX() || room.getStartX() == other.getEndX()) {
            start = Math.max(room.getStartY(), other.getStartY());
            end = Math.min(room.getEndY(), other.getEndY());

            if (end - start > 2) {
                mid = start + (end - start) / 2;
                grade[room.getEndX() == other.getStartX() ? room.getEndX() : room.getStartX()][mid] = 2;
            }
        }

        // Conexão vertical
        if (room.getEndY() == other.getStartY() || room.getStartY() == other.getEndY()) {
            start = Math.max(room.getStartX(), other.getStartX());
            end = Math.min(room.getEndX(), other.getEndX());

            if (end - start > 2) {
                mid = start + (end - start) / 2;
                grade[mid][room.getEndY() == other.getStartY() ? room.getEndY() : room.getStartY()] = 2;
            }
        }
    }

    public Texture generatePixmap() {

        for (int y = 0; y < grade.length; y++) {
            for (int x = 0; x < grade[0].length; x++) {
                switch (grade[y][x]) {
                    case 0:
                        pixmap.setColor(1, 1, 1, 1);
                        break;
                    case 1:
                        pixmap.setColor(0, 0, 0, 1);
                        break;
                    case 2:
                        pixmap.setColor(0, 0, 1, 1);
                        break;
                    default:
                        pixmap.setColor(1, 0, 0, 1);
                        break;
                }

                pixmap.fillRectangle(x, y, 1, 1);
            }
        }

        return new Texture(pixmap);
    }

    public void draw(Batch batch){
        batch.draw(texture, 200, 100, 25*texture.getWidth(), 25*texture.getHeight());
    }

    public void dispose(){
        pixmap.dispose();
    }


    public void printGrade() {
        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                System.out.print(grade[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void savePixmap(String fileName) {
        FileHandle file = Gdx.files.local("/maps/" + fileName);

        PixmapIO.writePNG(file, pixmap);

        System.out.println("Imagem salva em: " + file.file().getAbsolutePath());
    }
}
