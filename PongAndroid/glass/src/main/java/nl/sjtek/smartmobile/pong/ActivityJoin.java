package nl.sjtek.smartmobile.pong;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import nl.sjtek.smartmobile.libpong.game.MovementUpdate;
import nl.sjtek.smartmobile.libpong.ui.AsyncTaskClient;
import nl.sjtek.smartmobile.libpong.ui.GameView;
import nl.sjtek.smartmobile.libpong.ui.OnGameStateChangedListener;
import nl.sjtek.smartmobile.libpong.ui.Utils;


public class ActivityJoin extends Activity
        implements OnGameStateChangedListener, SensorEventListener {

    private AsyncTaskClient asyncTaskClient;
    private GameView gameView;

    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_join);

        asyncTaskClient = new AsyncTaskClient("192.168.0.101", 1337);
        asyncTaskClient.setListener(this);
        asyncTaskClient.execute();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_join, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGameChanged(State state) {
        switch (state) {
            case Waiting:
                break;
            case Connecting:
                break;
            case Running:
                setContentView(R.layout.fragment_game);
                gameView = (GameView) findViewById(R.id.gameView);
                break;
            case Stopping:
                break;
        }
    }

    private float valueSmooth = 0;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float valueMapped = Utils.map(sensorEvent.values[0], -2f, 2f, 565f, 0f);
        valueSmooth = Utils.exponentialSmoothing(valueMapped, valueSmooth, 0.1f);
        asyncTaskClient.sendMovementUpdate(new MovementUpdate((int) valueSmooth));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private class HandoverThread extends Thread {
        @Override
        public void run() {
            while (true) {
                gameView.setGameState(asyncTaskClient.getGameState());
                Thread.yield();
            }
        }
    }
}