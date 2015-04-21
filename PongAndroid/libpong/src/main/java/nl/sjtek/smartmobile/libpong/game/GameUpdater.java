package nl.sjtek.smartmobile.libpong.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * <h1>Game updater</h1>
 * Run a game cycle or update the UI.
 * <p>
 *     This class can run a game cycle (update ball and check for collisions) for both singleplayer
 *     and multiplayer. It can also draw Pong.
 * </p>
 * <p>
 *     This class is to be used in a {@link nl.sjtek.smartmobile.libpong.ui.GameView}.
 * </p>
 */
public class GameUpdater {

    public GameUpdater() {
    }

    /**
     * Run a game cycle for singleplayer
     * <p>
     *     Requires an X position for the bottom bat.<br />
     *     It will generate an Y position for the top bat.
     * </p>
     * @param gs The {@link nl.sjtek.smartmobile.libpong.game.GameState} to update
     * @param bottomBatX The position of the bottom bat
     */
    public static void update(GameState gs, int bottomBatX) {
        update(gs, bottomBatX, (gs.getBallX() - gs.getBatLength() / 2));
    }

    /**
     * Run a game cycle for multiplayer.
     * <b>Only for the host!</b>
     * @param gs The {@link nl.sjtek.smartmobile.libpong.game.GameState} to update
     * @param bottomBatX The position of the bottom bat
     * @param topBatX The position of the top bat
     */
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

    /**
     * Draw the game to a canvas.
     * @param canvas The canvas where we have to draw
     * @param paint Some paint
     * @param gs The {@link nl.sjtek.smartmobile.libpong.game.GameState} to draw
     * @param swapBats Swap the top and bottom bat. False for host. True for client.
     */
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
            bottomBatX = topBatX;
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
