package com.mygdx.proceduralGeneration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.scenes.Room;
import com.mygdx.utils.Consts;


import java.util.*;

public class GeracaoProcedural {

    private int[][] grade;

    private final Random rand;

    private List<Room> rooms;
    private int[][] roomGrafo;

    private Pixmap pixmap;
    private Texture texture;
    private final boolean saveImage = true;

    private final int minRoomArea = 100;
    private final int minRoomSide = 5;

    private int seed;

    private int imageCounter = 0;

    public GeracaoProcedural(int width, int height, int seed) {
        rand = new Random(seed);
        this.seed = seed;
        grade = new int[width][height];
        pixmap = new Pixmap(height, width, Pixmap.Format.RGBA8888);
        rooms = new ArrayList<>();
    }

    public GeracaoProcedural(int width, int height, Random rand) {
        this.rand = rand;
        grade = new int[width][height];
        rooms = new ArrayList<>();
    }

    public int[][] generate(){
        resetMap();
        generate(0, 0, grade.length - 1, grade[0].length - 1, rand.nextBoolean());
        generateGrafo();
        createEntrances();
        posProcess();
        return grade;
    }

    public void tccGenerate(){
        resetMap();
        generate(0, 0, grade.length - 1, grade[0].length - 1, rand.nextBoolean());
        generateGrafo();
        createEntrances();
        posProcess();
        fineTuning();
        texture = generatePixmap();

        if(saveImage) {
            savePixmap("mansionArticle.png");
        }
    }

    public void resetMap() {
        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                if (i == 0 || i == grade.length - 1 || j == 0 || j == grade[i].length - 1) {
                    grade[i][j] = 1;
                }
                else {
                    grade[i][j] = 0;
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

    private void generateGrafo(){
        roomGrafo = new int[rooms.size()][rooms.size()];

        for(int i = 0; i < roomGrafo.length; i++){
            for(int j = 0; j < roomGrafo[i].length; j++){
                if(rooms.get(i).isAdjascent(rooms.get(j))){
                    roomGrafo[i][j] = 1;
                }
            }
        }
    }

    private int[] generateCorridorGroups(){
        int[] corridorGroups = new int[rooms.size()];
        int group = 0;
        int node;
        Queue<Integer> nos = new LinkedList<>();

        for(int i = 0; i < rooms.size(); i++){
            if(corridorGroups[i] > 0 || rooms.get(i).getRoomType() != Consts.CORREDOR) continue;
            nos.offer(i);
            group++;

            do{
                node = nos.poll();
                corridorGroups[node] = group;

                for(int j = 0; j < roomGrafo[node].length; j++){
                    if(roomGrafo[node][j] == 1 && rooms.get(j).getRoomType() == Consts.CORREDOR && corridorGroups[j] == 0){
                        nos.offer(j);
                    }
                }

            }while(!nos.isEmpty());
        }

        return corridorGroups;
    }

    private void posProcess() {
        fixUnconectedRooms();
        openCorridors();
    }

    private void fineTuning(){
        boolean up, down, left, right;

        for(int i = 0; i < grade.length; i++){
            for(int j = 0; j < grade[i].length; j++){

                left = (i > 0) && (grade[i-1][j] == 1 || grade[i-1][j] == 2);
                right = (i < grade.length - 1) && (grade[i+1][j] == 1 || grade[i+1][j] == 2);
                down = (j > 0) && (grade[i][j-1] == 1 || grade[i][j-1] == 2);
                up = (j < grade[i].length - 1) && (grade[i][j+1] == 1 || grade[i][j+1] == 2);

                if(!left && !right && !down && !up && grade[i][j] != 0){
                    grade[i][j] = 0;
                    texture = generatePixmap();
                    imageCounter++;
                }
            }
        }
    }

    private void fixUnconectedRooms(){
        for(Room room : rooms){
            if(!room.isConected(grade)){
                for(Room other : rooms){
                    if(room == other)continue;

                    if(room.isAdjascent(other)){
                        if(criarEntrada(room, other)){
                            break;
                        }
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

            if (end - start >= 0) {
                // Remove parede vertical
                for (int k = start + 1; k < end; k++) {
                    grade[room.getEndX() == other.getStartX() ? room.getEndX() : room.getStartX()][k] = 0;
                }
                /*texture = generatePixmap();
                savePixmap(String.format("step_%03d.png", imageCounter));
                imageCounter++;*/
            }
        }

        // Conexão Horizontal (mesmo eixo Y)
        if (room.getEndY() == other.getStartY() || room.getStartY() == other.getEndY()) {
            start = Math.max(room.getStartX(), other.getStartX());
            end = Math.min(room.getEndX(), other.getEndX());

            if (end - start >= 0) {
                // Remove parede horizontal
                for (int k = start + 1; k < end; k++) {
                    grade[k][room.getEndY() == other.getStartY() ? room.getEndY() : room.getStartY()] = 0;
                }
                /*texture = generatePixmap();
                savePixmap(String.format("step_%03d.png", imageCounter));
                imageCounter++;*/
            }
        }
    }

    public void createEntrances() {
        int [] corridorGroup = generateCorridorGroups();
        List<Integer> conecteds = new ArrayList<>();

        for(int i = 0; i < roomGrafo.length; i++){
            if(rooms.get(i).getRoomType() == Consts.CORREDOR) continue;
            for(int j = 0; j < roomGrafo[i].length; j++){
                if (i == j || rooms.get(j).getRoomType() != Consts.CORREDOR) continue;
                if(roomGrafo[i][j] == 1 && !conecteds.contains(corridorGroup[j])) {
                    conecteds.add(corridorGroup[j]);
                    criarEntrada(rooms.get(i), rooms.get(j));
                }
            }
            conecteds.clear();
        }
    }

    private boolean criarEntrada(Room room, Room other) {
        int start, end, mid;

        // Conexão horizontal
        if (room.getEndX() == other.getStartX() || room.getStartX() == other.getEndX()) {
            start = Math.max(room.getStartY(), other.getStartY());
            end = Math.min(room.getEndY(), other.getEndY());

            if (end - start > 2) {
                mid = start + (end - start) / 2;
                grade[room.getEndX() == other.getStartX() ? room.getEndX() : room.getStartX()][mid] = 2;

                /*texture = generatePixmap();
                savePixmap(String.format("step_%03d.png", imageCounter));
                imageCounter++;*/
                return true;
            }
        }

        // Conexão vertical
        if (room.getEndY() == other.getStartY() || room.getStartY() == other.getEndY()) {
            start = Math.max(room.getStartX(), other.getStartX());
            end = Math.min(room.getEndX(), other.getEndX());

            if (end - start > 2) {
                mid = start + (end - start) / 2;
                grade[mid][room.getEndY() == other.getStartY() ? room.getEndY() : room.getStartY()] = 2;

                /*texture = generatePixmap();
                savePixmap(String.format("step_%03d.png", imageCounter));
                imageCounter++;*/
                return true;
            }
        }
        return false;
    }

    public int[][] getGrade(){
        return grade;
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
                        pixmap.setColor(1, 1, 1, 1);
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

    public int getSeed(){
        return seed;
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
        FileHandle file = Gdx.files.local("/tcc/" + fileName);

        PixmapIO.writePNG(file, pixmap);

        System.out.println("Imagem salva em: " + file.file().getAbsolutePath());
    }

    private void debug(){

    }
}