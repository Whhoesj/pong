package nl.sjtek.smartmobile.pong;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private final SurfaceHolder surfaceHolder;
    private final Paint paint;
    private final GameState gameState;

    public GameThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        paint = new Paint();
        gameState = new GameState();
    }

    @Override
    public void run() {

        while (true) {
            Canvas canvas = surfaceHolder.lockCanvas();
            gameState.update();
            gameState.draw(canvas, paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public void stopThread() {
        this.interrupt();
    }
}