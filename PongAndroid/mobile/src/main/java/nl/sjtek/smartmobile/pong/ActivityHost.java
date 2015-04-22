package nl.sjtek.smartmobile.pong;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;


public class ActivityHost extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.not_implemented);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
