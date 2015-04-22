package nl.sjtek.smartmobile.pong;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;


public class ActivityMain extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.buttonSingleplayer:
                Intent intentSingleplayer = new Intent(this, ActivitySingleplayer.class);
                startActivity(intentSingleplayer);
                break;
            case R.id.buttonHost:
                Intent intentHost = new Intent(this, ActivityHost.class);
                startActivity(intentHost);
                break;
            case R.id.buttonJoin:
                Intent intentJoin = new Intent(this, ActivityJoin.class);
                startActivity(intentJoin);
                break;
        }
    }
}
