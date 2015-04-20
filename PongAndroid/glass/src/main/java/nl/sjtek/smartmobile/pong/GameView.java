package nl.sjtek.smartmobile.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import nl.sjtek.smartmobile.pong.game.GameState;
import nl.sjtek.smartmobile.pong.game.GameUpdater;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final GameThread thread;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        thread = new GameThread(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void setBottomBatX(int x) {
        thread.setBottomBatX(x);
    }

    public void setTopBatX(int x) {
        thread.setTopBatX(x);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.stopThread();
    }

    public class GameThread extends Thread {

        private final SurfaceHolder surfaceHolder;
        private final Paint paint;
        private final GameState gameState;

        private boolean multiplayer;
        private boolean host;
        private int bottomBatX = 0;
        private int topBatX = 0;

        public GameThread(SurfaceHolder surfaceHolder, boolean host) {
            this(surfaceHolder);
            this.multiplayer = true;
            this.host = host;
        }

        public GameThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            this.paint = new Paint();
            this.gameState = new GameState();
            this.multiplayer = this.host = false;
        }

        public void setBottomBatX(int x) {
            this.bottomBatX = x;
        }

        public void setTopBatX(int x) {
            topBatX = x;
        }

        @Override
        public void run() {

            while (true) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (multiplayer && host) {
                    GameUpdater.update(gameState, bottomBatX, topBatX);
                } else {
                    GameUpdater.update(gameState, bottomBatX);
                }

                if (multiplayer && !host) {
                    GameUpdater.draw(canvas, paint, gameState, true);
                } else {
                    GameUpdater.draw(canvas, paint, gameState, false);
                }

                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void stopThread() {
            this.interrupt();
        }
    }
}