package nl.sjtek.smartmobile.libpong.net;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wouter on 22-4-15.
 */
public class Score {

    private static final String JSON_PLAYERNAME = "player";
    private static final String JSON_PLAYERSCORE = "playerScore";
    private static final String JSON_AISCORE = "aiScore";

    private String playerName;
    private int playerScore;
    private int aiScore;

    public Score(String playerName, int playerScore, int aiScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.aiScore = aiScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getAiScore() {
        return aiScore;
    }

    public String toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_PLAYERNAME, playerName);
        jsonObject.put(JSON_PLAYERSCORE, playerScore);
        jsonObject.put(JSON_AISCORE, aiScore);
        return jsonObject.toString();
    }

    public void send() throws JSONException, IOException {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://smartmobile.sjtek.nl/score.php");

        // Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("json", toJson()));
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        // Execute HTTP Post Request
        HttpResponse response = httpclient.execute(httppost);
        int responseCode = response.getStatusLine().getStatusCode();

        if (responseCode != 200) {
            Log.e("ScoreSender", "Response: " + responseCode);
        } else {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            String json = "";
            String line;
            while ((line = reader.readLine()) != null) {
                json += line;
            }

        }

    }
}
