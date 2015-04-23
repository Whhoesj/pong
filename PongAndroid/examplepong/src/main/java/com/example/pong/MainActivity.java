package com.example.pong;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import nl.sjtek.smartmobile.libpong.ui.PongView;
import nl.sjtek.smartmobile.libpong.ui.Utils;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    private PongView pongView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float smoothValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void onSensorChanged(SensorEvent sensorEvent) {
        float mappedValue = Utils.map(sensorEvent.values[1], -2, 2, 0, 100);
        smoothValue = Utils.exponentialSmoothing(smoothValue, mappedValue, 0.1f);
        pongView.setBottomBatX((int) smoothValue);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
