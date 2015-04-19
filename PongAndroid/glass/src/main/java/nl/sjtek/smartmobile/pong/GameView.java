package nl.sjtek.smartmobile.pong;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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

    public void changeBatSpeed(float batSpeed) {
        thread.getGameState().changeBatSpeed(batSpeed);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.stopThread();
    }
}