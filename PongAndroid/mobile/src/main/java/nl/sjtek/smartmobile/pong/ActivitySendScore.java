package nl.sjtek.smartmobile.pong;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import nl.sjtek.smartmobile.libpong.net.Score;
import nl.sjtek.smartmobile.libpong.net.ScoreSender;


public class ActivitySendScore extends ActionBarActivity {

    public static final String EXTRA_SCORE_PLAYER = "score_player";
    public static final String EXTRA_SCORE_AI = "score_ai";

    private int scorePlayer, scoreAi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_score);
        Bundle extras = getIntent().getExtras();
        scorePlayer = extras.getInt(EXTRA_SCORE_PLAYER);
        scoreAi = extras.getInt(EXTRA_SCORE_AI);
        TextView textviewScore = (TextView) findViewById(R.id.textViewScore);
        textviewScore.setText(String.valueOf(scorePlayer));
    }

    public void onClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editTextName);
        String name = editText.getText().toString();
        Score score = new Score(name, scorePlayer, scoreAi);
        new ScoreSender(score).execute();
        finish();
    }
}
