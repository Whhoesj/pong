package nl.sjtek.smartmobile.pong;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import nl.sjtek.smartmobile.libpong.net.Score;


public class ActivityScoreboard extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Loading...");
        new ScoreBoardTask().execute();
    }

    private class ScoreBoardTask extends AsyncTask<Void, Void, Void> {

        private List<Score> scores = null;
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
            if (scores != null) {

                String scoreString = "";
                for (Score score : scores) {
                   scoreString += score.toString() + "\n";
                }
                textView.setText(scoreString);
            }
        }
    }
}
