package nl.sjtek.smartmobile.pong;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import nl.sjtek.smartmobile.libpong.ui.GameView;
import nl.sjtek.smartmobile.libpong.ui.Utils;


public class ActivityGame extends Activity implements SensorEventListener {

    private GameView gameView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float valueSmooth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getCanonicalName(), "game start");
        setContentView(R.layout.activity_game);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        gameView = (GameView) findViewById(R.id.gameView);
        gameView.setSingleplayer();
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_exit:
                //TODO clean exit
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            openOptionsMenu();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float valueMapped = Utils.map(sensorEvent.values[0], -2f, 2f, 565f, 0f);
        valueSmooth = Utils.exponentialSmoothing(valueMapped, valueSmooth, 0.1f);
        gameView.setBottomBatX((int) valueSmooth);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
