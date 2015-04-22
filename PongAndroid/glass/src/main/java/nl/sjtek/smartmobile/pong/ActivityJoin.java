package nl.sjtek.smartmobile.pong;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;


public class ActivityJoin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_implemented);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}