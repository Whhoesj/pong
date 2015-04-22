package nl.sjtek.smartmobile.pong;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import nl.sjtek.smartmobile.libpong.net.Score;


public class ActivityMain extends ActionBarActivity {

    private ListView listViewScores;
    private ScoreAdapter scoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        listViewScores = (ListView) findViewById(R.id.listViewScore);
        new GetScoresAsyncTask(this).execute();
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

    private class GetScoresAsyncTask extends AsyncTask<Void, Void, Void> {

        private final Activity activity;
        private List<Score> scores;

        private GetScoresAsyncTask(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                scores = Score.downloadScoreboard();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            scoreAdapter = new ScoreAdapter(activity, scores);
            listViewScores.setAdapter(scoreAdapter);
        }
    }
}
