package nl.sjtek.smartmobile.pong;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread thread;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //So we can listen for events...
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        //and instantiate the thread
        thread = new GameThread(holder, context, new Handler());
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        //Mandatory, just swallowing it for this example

    }

    public void changeBatSpeed(float batSpeed) {
        thread.getGameState().changeBatSpeed(batSpeed);
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.start();
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.stopThread();
    }
}