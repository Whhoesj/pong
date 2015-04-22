package nl.sjtek.smartmobile.pong;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import nl.sjtek.smartmobile.libpong.game.PongState;
import nl.sjtek.smartmobile.libpong.ui.PongView;
import nl.sjtek.smartmobile.libpong.ui.Utils;


public class ActivitySingleplayer extends Activity implements SensorEventListener {

    private PongView pongView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float valueSmooth = 0;
    private boolean done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getCanonicalName(), "game start");
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
        pongView.setBottomBatX((int) valueSmooth);

        PongState pongState = pongView.getPongState();
        int scoreBottom = pongState.getScoreBottom();
        int scoreTop = pongState.getScoreTop();
        if ((scoreBottom > 20 || scoreTop > 20) && !done) {
            done = true;
            Intent scoreIntent = new Intent(this, ActivitySendScore.class);
            scoreIntent.putExtra(ActivitySendScore.EXTRA_SCOREPLAYER, scoreBottom);
            scoreIntent.putExtra(ActivitySendScore.EXTRA_SCOREAI, scoreTop);
            startActivity(scoreIntent);
            finish();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
