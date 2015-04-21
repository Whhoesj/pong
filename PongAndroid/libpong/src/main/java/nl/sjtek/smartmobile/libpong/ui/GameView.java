package nl.sjtek.smartmobile.libpong.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import nl.sjtek.smartmobile.libpong.game.GameState;
import nl.sjtek.smartmobile.libpong.game.GameUpdater;

/**
 * A view to display pong
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread = new GameThread();
    private SurfaceHolder surfaceHolder;
    private GameState gameState = new GameState();

    private boolean delayedStart = true;
    private boolean multiplayer;
    private boolean host;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public void setSingleplayer() {
        multiplayer = host = false;
        delayedStart = false;
    }

    public void setMultiplayer(boolean isHost) {
        multiplayer = true;
        host = isHost;
        delayedStart = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.stopThread();
    }

    /**
     * Change the X position of the bottom bat.
     * @param x New X position of the bottom bat
     */
    public void setBottomBatX(int x) {
        thread.setBottomBatX(x);
    }

    /**
     * Change the X position of the top bat.
     * <p>Only for multiplayer.</p>
     * @param x New X position of the top bat
     */
    public void setTopBatX(int x) {
        thread.setTopBatX(x);
    }

    /**
     * Get the current {@link GameState}.
     * @return The GameState
     */
    public GameState getGameState() {
        return gameState;
    }


    private class GameThread extends Thread {

        private int bottomBatX = 0;
        private int topBatX = 0;

        private boolean running = true;

        public void setBottomBatX(int x) {
            this.bottomBatX = x;
        }

        public void setTopBatX(int x) {
            topBatX = x;
        }

        @Override
        public void run() {

            while (delayedStart);

            while (running) {
                Canvas canvas = getHolder().lockCanvas();
                if (multiplayer && host) {
                    GameUpdater.update(gameState, bottomBatX, topBatX);
                } else {
                    GameUpdater.update(gameState, bottomBatX);
                }

                if (multiplayer && !host) {
                    GameUpdater.draw(canvas, gameState, true);
                } else {
                    GameUpdater.draw(canvas, gameState, false);
                }

                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void stopThread() {
            running = false;
        }
    }
}