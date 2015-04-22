package nl.sjtek.smartmobile.libpong.net;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains data about a score record
 */
public class Score implements Comparable<Score> {

    private static final String JSON_PLAYERNAME = "player";
    private static final String JSON_PLAYERSCORE = "playerScore";
    private static final String JSON_AISCORE = "aiScore";

    private String playerName;
    private int playerScore;
    private int aiScore;

    /**
     * Create a new score record
     *
     * @param playerName  Name of the player
     * @param playerScore Score of the player
     * @param aiScore     Score of the AI
     */
    public Score(String playerName, int playerScore, int aiScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
        this.aiScore = aiScore;
    }

    /**
     * Create a new score record
     *
     * @param json A JSON {@link java.lang.String} to parse
     * @throws JSONException Parsing error
     */
    public Score(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        this.playerName = jsonObject.getString(JSON_PLAYERNAME);
        this.playerScore = jsonObject.getInt(JSON_PLAYERSCORE);
        this.aiScore = jsonObject.getInt(JSON_AISCORE);
    }

    /**
     * Get a {@link java.util.List} of {@link nl.sjtek.smartmobile.libpong.net.Score} of the Sjtek server.
     * <p>
     * If the response code is not 200. It will return null
     * </p>
     *
     * @return List with scores. Null if response is not 200.
     * @throws IOException   IO error
     * @throws JSONException JSON parse error
     */
    public static List<Score> downloadScoreboard() throws IOException, JSONException {
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://smartmobile.sjtek.nl/getScoreboard.php");

        HttpResponse response = client.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == 200) {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
            String json = "";
            String line;
            while ((line = reader.readLine()) != null) {
                json += line;
            }

            JSONArray jsonArray = new JSONArray(json);

            List<Score> scores = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                scores.add(new Score(jsonArray.get(i).toString()));
            }

            return scores;
        } else {
            Log.e("DownloadScoreboard", "Response: " + response.getStatusLine().getStatusCode());
            return null;
        }
    }

    /**
     * Get the name of the player
     *
     * @return Player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Get the score of the player
     *
     * @return Score of the player
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * Get the score of the AI
     *
     * @return Score of the AI
     */
    public int getAiScore() {
        return aiScore;
    }

    /**
     * Generate a JSON string from the object
     *
     * @return JSON String
     * @throws JSONException JSON parsing error
     */
    public String toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_PLAYERNAME, playerName);
        jsonObject.put(JSON_PLAYERSCORE, playerScore);
        jsonObject.put(JSON_AISCORE, aiScore);
        return jsonObject.toString();
    }

    /**
     * Send the score to the Sjtek server
     *
     * @throws JSONException JSON parse error
     * @throws IOException   IO error
     */
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
        }

    }

    /**
     * Create a string of the score.
     * <p>
     * Format: "Name: [playerName] Score: [playerScore]"
     * </p>
     *
     * @return String of score
     */
    @Override
    public String toString() {
        return "Name: " + playerName + " Score: " + playerScore;
    }

    /**
     * Compare to an other {@link nl.sjtek.smartmobile.libpong.net.Score} object.
     * <p>
     * Returns:<br>
     * <ul>
     * <li>-1 if greater score</li>
     * <li>0 if equal score</li>
     * <li>1 if lesser score</li>
     * </ul>
     * </p>
     *
     * @param score The other score object
     * @return
     */
    @Override
    public int compareTo(Score score) {
        int score1 = playerScore;
        int score2 = score.getPlayerScore();

        if (score1 > score2) {
            return -1;
        } else if (score1 < score2) {
            return 1;
        } else {
            return 0;
        }
    }
}
