package nl.sjtek.smartmobile.pong.server;

import nl.sjtek.smartmobile.pong.data.GameUpdate;
import nl.sjtek.smartmobile.pong.data.MovementUpdate;

/**
 * Created by wouter on 16-4-15.
 */
public class GameState {

    private long tick = 0;

    //screen width and height
    private final int screenWidth = 640;
    private final int screenHeight = 360;

    //The ball
    private final int ballSize = 10;
    private int ballX = 100;
    private int ballY = 100;
    private int ballVelocityX = 3;
    private int ballVelocityY = 3;

    //The bats
    private final int batLength = 75;
    private final int batHeight = 10;
    private final int batMargin = 10;

    private int topBatX = (screenWidth / 2) - (batLength / 2);
    private final int topBatY = batMargin;

    private int bottomBatX = (screenWidth / 2) - (batLength / 2);
    private final int bottomBatY = screenHeight - batHeight - batMargin;

    public void setTopBat(MovementUpdate movementUpdate) {
        topBatX = (int)movementUpdate.getMovementValue();
    }

    public void setBottomBat(MovementUpdate movementUpdate) {
        bottomBatX = (int)movementUpdate.getMovementValue();
    }

    public void update() {

        ballX += ballVelocityX;
        ballY += ballVelocityY;

        topBatX = ballX - batLength/2;

        //DEATH!
        if (ballY > screenHeight - (ballSize/2) || ballY < ballSize/2) {
            ballX = 100;
            ballY = 100;
        }    //Collisions with the sides

        if (ballX > screenWidth || ballX < 0)
            ballVelocityX *= -1;    //Collisions with the bats

        if (ballX > topBatX && ballX < topBatX + batLength && ballY < topBatY)
            ballVelocityY *= -1;  //Collisions with the bats

        if (ballX > bottomBatX && ballX < bottomBatX + batLength
                && ballY > bottomBatY)
            ballVelocityY *= -1;

        tick++;
    }

    public GameUpdate getUpdate() {
        GameUpdate update;
        update = new GameUpdate(tick, ballX, ballY, topBatX, topBatY, bottomBatX, bottomBatY);
        return update;
    }

}
