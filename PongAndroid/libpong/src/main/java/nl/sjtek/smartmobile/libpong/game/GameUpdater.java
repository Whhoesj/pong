package nl.sjtek.smartmobile.libpong.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * <h1>Game updater</h1>
 * Run a game cycle or update the UI.
 * <p>
 * This class can run a game cycle (update ball and check for collisions) for both singleplayer
 * and multiplayer. It can also draw Pong.
 * </p>
 * <p>
 * This class is to be used in a {@link nl.sjtek.smartmobile.libpong.ui.PongView}.
 * </p>
 */
public class GameUpdater {

    public GameUpdater() {
    }

    /**
     * Run a game cycle for singleplayer
     * <p>
     * Requires an X position for the bottom bat.<br>
     * It will generate an Y position for the top bat.
     * </p>
     *
     * @param pongState  The {@link PongState} to update
     * @param bottomBatX The position of the bottom bat
     */
    public static void update(PongState pongState, int bottomBatX) {
        int ballX = pongState.getBallX() - (pongState.getBatLength() / 2);
        int topBatX = pongState.getTopBatX();
        int newTopBatX = topBatX;
        int ballVelocityX = pongState.getBallVelocityX() - 1;
        if (topBatX > ballX) {
            newTopBatX += -Math.abs(ballVelocityX);
        } else if (topBatX < ballX) {
            newTopBatX += Math.abs(ballVelocityX);
        }
        update(pongState, bottomBatX, newTopBatX);
    }

    /**
     * Run a game cycle for multiplayer.
     * <b>Only for the host!</b>
     *
     * @param ps         The {@link PongState} to update
     * @param bottomBatX The position of the bottom bat
     * @param topBatX    The position of the top bat
     */
    public static void update(PongState ps, int bottomBatX, int topBatX) {

        ps.setBallX(ps.getBallX() + ps.getBallVelocityX());
        ps.setBallY(ps.getBallY() + ps.getBallVelocityY());

        ps.setTopBatX(topBatX);
        ps.setBottomBatX(bottomBatX);

        //Death

        if (ps.getBallY() > ps.getScreenHeight() - (ps.getBallSize() / 2)) {
            //Collisions with the sides
            ps.winTop();
            ps.setBallX(100);
            ps.setBallY(ps.getScreenHeight() / 2);
        }

        if (ps.getBallY() < ps.getBallSize() / 2) {
            //Collisions with the sides
            ps.winBottom();
            ps.setBallX(100);
            ps.setBallY(ps.getScreenHeight() / 2);
        }

        if (ps.getBallX() > ps.getScreenWidth() || ps.getBallX() < 0) {
            //Collisions with the bats
            ps.setBallVelocityX(ps.getBallVelocityX() * -1);
        }

        if (ps.getBallX() > ps.getTopBatX() &&
                ps.getBallX() < ps.getTopBatX() + ps.getBatLength() &&
                ps.getBallY() < ps.getTopBatY()) {
            //Collisions with the bats
            ps.setBallVelocityY(ps.getBallVelocityY() * -1);
        }

        if (ps.getBallX() > bottomBatX &&
                ps.getBallX() < bottomBatX + ps.getBatLength() &&
                ps.getBallY() > ps.getBottomBatY()) {
            ps.setBallVelocityY(ps.getBallVelocityY() * -1);
        }
    }

    /**
     * Draw the game to a canvas.
     *
     * @param canvas   The canvas where we have to draw
     * @param gs       The {@link PongState} to draw
     * @param swapBats Swap the top and bottom bat. False for host. True for client.
     */
    public static void draw(Canvas canvas, final PongState gs, boolean swapBats) {

        Paint pongPaint = new Paint();
        Paint textPaint = new Paint();

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
        pongPaint.setARGB(200, 255, 255, 255);
        textPaint.setARGB(200, 153, 153, 153);

        //draw the ball
        canvas.drawRect(new Rect(ballX, ballY, ballX + ballSize, ballY + ballSize),
                pongPaint);

        //draw the bats
        canvas.drawRect(new Rect(topBatX, topBatY, topBatX + batLength,
                topBatY + batHeight), pongPaint); //top bat
        canvas.drawRect(new Rect(bottomBatX, bottomBatY, bottomBatX + batLength,
                bottomBatY + batHeight), pongPaint); //bottom bat

        textPaint.setTextSize(50f);
        canvas.drawText(String.valueOf(gs.getScoreTop()), 100, 150, textPaint);
        canvas.drawText(String.valueOf(gs.getScoreBottom()), 100, gs.getScreenHeight() - 150, textPaint);
    }
}
