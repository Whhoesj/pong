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
import android.widget.TextView;


public class ActivityGame extends Activity implements SensorEventListener {

    private GameView gameView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView textViewZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getCanonicalName(), "game start");
        setContentView(R.layout.activity_game);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        textViewZ = (TextView) findViewById(R.id.textViewZ);
        gameView = (GameView) findViewById(R.id.gameView);
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
                // exit game
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

    public float power(final float base, final int power) {
        float result = 1;
        for( int i = 0; i < power; i++ ) {
            result *= base;
        }
        return result;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float valueZ = sensorEvent.values[0];

        float value = -valueZ;
        value = value * 8;
        if (value > 10) {
            value = (float) 10;
        } else if (value < -10) {
            value = (float) -10;
        }
        gameView.changeBatSpeed(value);
//        textViewZ.setText(String.valueOf(value));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
