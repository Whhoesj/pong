package nl.sjtek.smartmobile.pong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.sjtek.smartmobile.libpong.net.Score;

/**
 * Created by wouter on 22-4-15.
 */
public class ScoreAdapter extends ArrayAdapter<Score> {

    private Context context;
    private List<Score> scores;

    public ScoreAdapter(Context context, List<Score> scores) {
        super(context, R.layout.score_list_item, scores);
        this.context = context;
        this.scores = scores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Score currentScore = scores.get(position);
        View scoreItemView = inflater.inflate(R.layout.score_list_item, null);

        TextView textViewName = (TextView) scoreItemView.findViewById(R.id.textViewName);
        textViewName.setText(currentScore.getPlayerName());

        TextView textViewScore = (TextView) scoreItemView.findViewById(R.id.textViewScore);
        textViewScore.setText(String.valueOf(currentScore.getPlayerScore()));


        return scoreItemView;
    }
}
