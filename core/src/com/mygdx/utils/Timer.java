package com.mygdx.utils;

public class Timer {

    private float tempoAcumulado;
    private float breakTime = 0.5f;

    public Timer(float breakTime) {
        this.breakTime = breakTime;
    }

    public boolean checkTimer(float deltaTime) {
        tempoAcumulado += deltaTime;
        if (tempoAcumulado >= breakTime) {
            tempoAcumulado = 0;
            return true;
        }
        return false;
    }

    public void reset(){
        tempoAcumulado = 0;
    }

}
