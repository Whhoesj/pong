package nl.sjtek.smartmobile.pong;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;


public class ActivitySensor extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView textX, textY, textZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvitiy_sensor);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        textX = (TextView) findViewById(R.id.textViewX);
        textY = (TextView) findViewById(R.id.textViewY);
        textZ = (TextView) findViewById(R.id.textViewZ);

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
        Float valueX = sensorEvent.values[0];
        Float valueY = sensorEvent.values[1];
        Float valueZ = sensorEvent.values[2];

        textX.setText(valueX.toString());
        textY.setText(valueY.toString());
        textZ.setText(valueZ.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
