package nl.sjtek.smartmobile.pong.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by wouter on 20-4-15.
 */
public class GameUpdater {

    public GameUpdater() {
    }

    public static void update(GameState gs, int bottomBatX) {
        update(gs, bottomBatX, (gs.getBallX() - gs.getBatLength() / 2));
    }

    public static void update(GameState gs, int bottomBatX, int topBatX) {

        gs.setBallX(gs.getBallX() + gs.getBallVelocityX());
        gs.setBallY(gs.getBallY() + gs.getBallVelocityY());

        gs.setTopBatX(topBatX);
        gs.setBottomBatX(bottomBatX);

        //Death

        if (gs.getBallY() > gs.getScreenHeight() - (gs.getBallSize() / 2)) {
            //Collisions with the sides
            gs.winTop();
            gs.setBallX(100);
            gs.setBallY(gs.getScreenHeight() / 2);
        }

        if (gs.getBallY() < gs.getBallSize() / 2) {
            //Collisions with the sides
            gs.winBottom();
            gs.setBallX(100);
            gs.setBallY(gs.getScreenHeight() / 2);
        }

        if (gs.getBallX() > gs.getScreenWidth() || gs.getBallX() < 0) {
            //Collisions with the bats
            gs.setBallVelocityX(gs.getBallVelocityX() * -1);
        }

        if (gs.getBallX() > gs.getTopBatX() && gs.getBallX() < gs.getTopBatX() + gs.getBatLength() && gs.getBallY() < gs.getTopBatY()) {
            //Collisions with the bats
            gs.setBallVelocityY(gs.getBallVelocityY() * -1);
        }

        if (gs.getBallX() > bottomBatX && gs.getBallX() < bottomBatX + gs.getBatLength() && gs.getBallY() > gs.getBottomBatY()) {
            gs.setBallVelocityY(gs.getBallVelocityY() * -1);
        }
    }

    public static void draw(Canvas canvas, Paint paint, final GameState gs, boolean swapBats) {

        final int ballSize = gs.getBallSize();
        final int ballX = gs.getBallX();
        final int ballY = gs.getBallY();
        final int topBatY = gs.getTopBatY();
        final int bottomBatY = gs.getBottomBatY();
        int topBatX = gs.getTopBatX();
        int bottomBatX = gs.getBottomBatX();
        final int batLength = gs.getBatLength();
        final int batHeight = gs.getBatHeight();

        if (swapBats) {
            int oldBottomBatX = bottomBatX;
            int oldTopBatX = topBatX;
            bottomBatX = oldTopBatX;
            topBatX = oldBottomBatX;
        }

        //Clear the screen
        canvas.drawRGB(0, 0, 0);

        //set the colour
        paint.setARGB(200, 255, 255, 255);

        //draw the ball
        canvas.drawRect(new Rect(ballX, ballY, ballX + ballSize, ballY + ballSize),
                paint);

        //draw the bats
        canvas.drawRect(new Rect(topBatX, topBatY, topBatX + batLength,
                topBatY + batHeight), paint); //top bat
        canvas.drawRect(new Rect(bottomBatX, bottomBatY, bottomBatX + batLength,
                bottomBatY + batHeight), paint); //bottom bat
    }
}
