package nl.sjtek.smartmobile.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.SurfaceHolder;

import nl.sjtek.smartmobile.pong.data.GameUpdate;

/**
 * Created by wouter on 16-4-15.
 */
public class OnlineGameThread implements Runnable {

    /** Handle to the surface manager object we interact with */
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private GameUpdate gameUpdate = new GameUpdate();

    public OnlineGameThread(SurfaceHolder surfaceHolder, Context context, Handler handler)
    {
        this.surfaceHolder = surfaceHolder;
        paint = new Paint();
    }

    public void setGameUpdate(GameUpdate gameUpdate) {
        this.gameUpdate = gameUpdate;
    }

    @Override
    public void run() {

        while (true) {
            Canvas canvas = surfaceHolder.lockCanvas();

            //Clear the screen
            canvas.drawRGB(0, 0, 0);

            //set the colour
            paint.setARGB(200, 255, 255, 255);

            //draw the ball
            int ballX = gameUpdate.getBallX();
            int ballY = gameUpdate.getBallY();
            int ballSize = gameUpdate.getBallSize();
            canvas.drawRect(new Rect(ballX, ballY, ballX + ballSize, ballY + ballSize),
                    paint);

            //draw the bats
            int topBatX = gameUpdate.getTopBatX();
            int topBatY = gameUpdate.getTopBatY();
            int batLength = gameUpdate.getBatLength();
            int batHeight = gameUpdate.getBatHeight();
            int bottomBatX = gameUpdate.getBottomBatX();
            int bottomBatY = gameUpdate.getBottomBatY();
            canvas.drawRect(new Rect(topBatX, topBatY, topBatX + batLength,
                    topBatY + batHeight), paint); //top bat

            canvas.drawRect(new Rect(bottomBatX, bottomBatY, bottomBatX + batLength,
                    bottomBatY + batHeight), paint); //bottom bat

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
