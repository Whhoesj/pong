package nl.sjtek.smartmobile.pong;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import nl.sjtek.smartmobile.pong.data.GameUpdate;
import nl.sjtek.smartmobile.pong.data.MovementUpdate;

/**
 * Created by wouter on 16-4-15.
 */
public class OnlineGame {

    private static final String serverAddress = "192.168.0.107";
    private static final int port = 1337;

    private final UUID uuid = UUID.randomUUID();
    private GameUpdate gameUpdate = new GameUpdate(uuid);
    private float movementValue = 0;

    private MovementAsyncTask movementAsyncTask;
    private UpdateAsyncTask updateAsyncTask;

    public OnlineGame() {
        movementAsyncTask = new MovementAsyncTask();
        updateAsyncTask = new UpdateAsyncTask();
    }

    public void start() {
        movementAsyncTask.execute();
        updateAsyncTask.execute();
    }

    public GameUpdate getGameUpdate() {
        return gameUpdate;
    }

    public void setMovementValue(float movementValue) {
        this.movementValue = movementValue;
    }

    private class MovementAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Socket socket = new Socket(serverAddress, port);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(gameUpdate);

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    GameUpdate receivedGameUpdate = (GameUpdate) inputStream.readObject();
                    gameUpdate = receivedGameUpdate;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Socket socket = new Socket(serverAddress, port);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

                while (true) {
                    MovementUpdate movementUpdate = new MovementUpdate(uuid, movementValue);
                    outputStream.writeObject(movementUpdate);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
