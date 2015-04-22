package nl.sjtek.smartmobile.libpong.game;

import java.io.Serializable;

/**
 * Contains variables for a game of Pong.
 * <p>
 * This file contains all variables needed to draw a game of Pong.<br />
 * To be used in a {@link nl.sjtek.smartmobile.libpong.game.GameUpdater}.
 * </p>
 */
public class PongState implements Serializable {

    private final int screenWidth = 640;
    private final int screenHeight = 360;

    private final int ballSize = 10;
    private final int batLength = 75;
    private int topBatX = (screenWidth / 2) - (batLength / 2);
    private int bottomBatX = (screenWidth / 2) - (batLength / 2);
    private final int batHeight = 10;
    private final int batMargin = 10;
    private final int topBatY = batMargin;
    private final int bottomBatY = screenHeight - batHeight - batMargin;
    private int ballX = 100;
    private int ballY = 100;
    private int ballVelocityX = 3;
    private int ballVelocityY = 3;

    private int scoreBottom = 0;
    private int scoreTop = 0;

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getBallSize() {
        return ballSize;
    }

    public int getBatLength() {
        return batLength;
    }

    public int getTopBatX() {
        return topBatX;
    }

    public void setTopBatX(int topBatX) {
        this.topBatX = topBatX;
    }

    public int getBottomBatX() {
        return bottomBatX;
    }

    public void setBottomBatX(int bottomBatX) {
        this.bottomBatX = bottomBatX;
    }

    public int getBatHeight() {
        return batHeight;
    }

    public int getBatMargin() {
        return batMargin;
    }

    public int getTopBatY() {
        return topBatY;
    }

    public int getBottomBatY() {
        return bottomBatY;
    }

    public int getBallX() {
        return ballX;
    }

    public void setBallX(int ballX) {
        this.ballX = ballX;
    }

    public int getBallY() {
        return ballY;
    }

    public void setBallY(int ballY) {
        this.ballY = ballY;
    }

    public int getBallVelocityX() {
        return ballVelocityX;
    }

    public void setBallVelocityX(int ballVelocityX) {
        this.ballVelocityX = ballVelocityX;
    }

    public int getBallVelocityY() {
        return ballVelocityY;
    }

    public void setBallVelocityY(int ballVelocityY) {
        this.ballVelocityY = ballVelocityY;
    }

    public int getScoreBottom() {
        return scoreBottom;
    }

    public void setScoreBottom(int scoreBottom) {
        this.scoreBottom = scoreBottom;
    }

    public int getScoreTop() {
        return scoreTop;
    }

    public void setScoreTop(int scoreTop) {
        this.scoreTop = scoreTop;
    }

    public void winBottom() {
        scoreBottom++;
    }

    public void winTop() {
        scoreTop++;
    }

    public void scoreReset() {
        scoreBottom = scoreTop = 0;
    }
}
