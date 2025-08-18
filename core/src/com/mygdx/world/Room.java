package com.mygdx.world;

import com.mygdx.core.Consts;

public class Room {

    private int startX, startY, endX, endY;
    private byte roomType;


    public Room(int startX, int startY, int endX, int endY, int minRoomSide){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        if(endX-startX < minRoomSide || endY-startY < minRoomSide){
            roomType = Consts.CORREDOR;
        }
        else{
            roomType = Consts.QUARTO;
        }
    }

    public byte getRoomType(){
        return roomType;
    }

    public int[] getCenter(){
        return new int[]{(endX + startX)/2, (endY + startY)/2};
    }

    public int getStartX(){
        return startX;
    }
    public int getStartY(){
        return startY;
    }
    public int getEndX(){
        return endX;
    }
    public int getEndY(){
        return endY;
    }

    public boolean isConected(int[][] grade){
        for (int i = startX; i <= endX; i++) {
            if (grade[i][startY] == 2 || grade[i][endY] == 2) {
                return true;
            }
        }

        for (int j = startY; j <= endY; j++) {
            if (grade[startX][j] == 2 || grade[endX][j] == 2) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdjascent(Room other) {
        if (this.endY == other.startY || this.startY == other.endY) {
            if ((this.startX <= other.endX && this.endX >= other.startX)) {
                return true;
            }
        }

        if (this.endX == other.startX || this.startX == other.endX) {
            if ((this.startY <= other.endY && this.endY >= other.startY)) {
                return true;
            }
        }

        return false;
    }

    public String toString(){
        return "startX: " + startX + ", startY: " + startY + ", endX: " + endX + ", endY: " + endY;
    }

}
