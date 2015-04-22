package nl.sjtek.smartmobile.pong;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import nl.sjtek.smartmobile.libpong.ui.PongView;
import nl.sjtek.smartmobile.libpong.ui.Utils;


public class ActivitySingleplayer extends ActionBarActivity implements SensorEventListener {

    private PongView pongView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float valueSmooth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleplayer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        pongView = (PongView) findViewById(R.id.pongView);
        pongView.setSingleplayer();
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
        getMenuInflater().inflate(R.menu.menu_activity_singleplayer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float valueMapped = Utils.map(sensorEvent.values[0], -2f, 2f, 565f, 0f);
        valueSmooth = Utils.exponentialSmoothing(valueMapped, valueSmooth, 0.1f);
        pongView.setBottomBatX((int) valueSmooth);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
