package nl.sjtek.smartmobile.pong.data;

import java.io.Serializable;


public class GameUpdate implements Serializable {

    private int balX, balY;
    private int topBatX, topBatY;
    private int bottomBatX, bottomBatY;
    private boolean doClose = false;

    public GameUpdate(int balX, int balY, int topBatX, int topBatY, int bottomBatX, int bottomBatY) {
        this.balX = balX;
        this.balY = balY;
        this.topBatX = topBatX;
        this.topBatY = topBatY;
        this.bottomBatX = bottomBatX;
        this.bottomBatY = bottomBatY;
    }

    public GameUpdate(int balX, int balY, int topBatX, int topBatY, int bottomBatX, int bottomBatY, boolean doClose) {
        this(balX, balY, topBatX, topBatY, bottomBatX, bottomBatY);
        this.doClose = doClose;
    }

    public int getBalX() {
        return balX;
    }

    public int getBalY() {
        return balY;
    }

    public int getTopBatX() {
        return topBatX;
    }

    public int getTopBatY() {
        return topBatY;
    }

    public int getBottomBatX() {
        return bottomBatX;
    }

    public int getBottomBatY() {
        return bottomBatY;
    }

    public boolean isDoClose() {
        return doClose;
    }
}
