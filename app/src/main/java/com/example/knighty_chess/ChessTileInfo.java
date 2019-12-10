package com.example.knighty_chess;

public class ChessTileInfo {
    private int x = 0;
    private int y = 0;
    private boolean isSource = false;
    private boolean isTarget = false;

    ChessTileInfo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSource() {
        return isSource;
    }

    public void setSource(boolean source) {
        isSource = source;
    }

    public boolean isTarget() {
        return isTarget;
    }

    public void setTarget(boolean target) {
        isTarget = target;
    }

    public void clearSelection(){
        isTarget = false;
        isSource = false;
    }
}
