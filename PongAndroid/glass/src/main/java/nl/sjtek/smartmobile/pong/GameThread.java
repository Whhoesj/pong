package nl.sjtek.smartmobile.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    /** Handle to the surface manager object we interact with */
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private GameState gameState;

    public GameThread(SurfaceHolder surfaceHolder, Context context, Handler handler)
    {
        this.surfaceHolder = surfaceHolder;
        paint = new Paint();
        gameState = new GameState();
    }

    @Override
    public void run() {

        //TODO fix close crash
        while (true) {
            if (!Thread.interrupted()) {
                Canvas canvas = surfaceHolder.lockCanvas();
                if (!Thread.interrupted()) gameState.update();
                if (!Thread.interrupted()) gameState.draw(canvas, paint);
                if (!Thread.interrupted()) surfaceHolder.unlockCanvasAndPost(canvas);
            } else {
                return;
            }
        }
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public void stopThread() {
        this.interrupt();
    }
}