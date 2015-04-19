package nl.sjtek.smartmobile.pong;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import nl.sjtek.smartmobile.pong.data.GameUpdate;

/**
 * Created by wouter on 16-4-15.
 */
public class OnlineGameView extends SurfaceView implements SurfaceHolder.Callback {

    private OnlineGameThread thread;

    public OnlineGameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //So we can listen for events...
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        //and instantiate the thread
        thread = new OnlineGameThread(holder, context, new Handler());
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        //Mandatory, just swallowing it for this example

    }

    public void setGameUpdate(GameUpdate gameUpdate) {
        thread.setGameUpdate(gameUpdate);
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.run();
    }

    //Implemented as part of the SurfaceHolder.Callback interface
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //TODO stop thread
//        thread.stop();
    }
}
