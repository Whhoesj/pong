package nl.sjtek.smartmobile.pong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.widget.TextView;

import java.util.List;

import nl.sjtek.smartmobile.libpong.net.Score;
import nl.sjtek.smartmobile.libpong.net.ScoreSender;


public class ActivitySendScore extends Activity {

    public static final String EXTRA_SCOREPLAYER = "score_player";
    public static final String EXTRA_SCOREAI = "score_ai";
    private static final int SPEECH_REQUEST = 0;
    private String name;
    private int scorePlayer;
    private int scoreAi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_score);
        Bundle extras = getIntent().getExtras();
        TextView textViewScorePlayer = (TextView) findViewById(R.id.textViewScore);
        scorePlayer = extras.getInt(EXTRA_SCOREPLAYER);
        scoreAi = extras.getInt(EXTRA_SCOREAI);
        displaySpeechRecognizer();
    }

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        startActivityForResult(intent, SPEECH_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            name = spokenText;
            TextView textViewName = (TextView) findViewById(R.id.textViewName);
            textViewName.setText("Name: " + spokenText);


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            new ScoreSender(new Score(name, scorePlayer, scoreAi)).execute();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
