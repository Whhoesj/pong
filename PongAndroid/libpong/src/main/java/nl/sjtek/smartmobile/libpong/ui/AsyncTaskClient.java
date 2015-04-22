package nl.sjtek.smartmobile.libpong.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import nl.sjtek.smartmobile.libpong.game.GameState;
import nl.sjtek.smartmobile.libpong.game.MovementUpdate;

/**
 * An {@link android.os.AsyncTask} to connect to an Pong host. ({@link nl.sjtek.smartmobile.libpong.ui.AsyncTaskHost})
 * <p>
 *     This {@link android.os.AsyncTask} will connect to a Pong host. The constructor takes the connection parameters.<br>
 *     Bat movement can be send with {@link AsyncTaskClient#sendMovementUpdate(nl.sjtek.smartmobile.libpong.game.MovementUpdate)}.<br>
 *     The latest received {@link nl.sjtek.smartmobile.libpong.game.GameState} can be requested with {@link AsyncTaskClient#getGameState()}.
 * </p>
 */
public class AsyncTaskClient extends AsyncTask<Void, Void, Void> {

    private static final String DEBUG_TAG = "AsyncTaskClient";

    private final GameView gameView;
    private final String serverAddress;
    private final int serverPort;

    private GameState gameState = new GameState();
    private MovementUpdate movementUpdate;
    private boolean running = true;

    private OnGameStateChangedListener listener;

    /**
     * Setup the {@link android.os.AsyncTask}.
     * @param serverAddress The address to connect to
     * @param serverPort The port to connect to
     * @param listener Callbacks for connection changes
     */
    public AsyncTaskClient(String serverAddress, int serverPort,
                           OnGameStateChangedListener listener, GameView gameView) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.listener = listener;
        this.gameView = gameView;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        StateReceiverThread stateReceiverThread;
        MovementSenderThread movementSenderThread;

        try {
            if (listener != null) listener.onGameChanged(OnGameStateChangedListener.State.Connecting);
            Log.d(DEBUG_TAG, "Connecting...");
            stateReceiverThread = new StateReceiverThread(
                    new Socket(serverAddress, serverPort));
            movementSenderThread = new MovementSenderThread(
                    new Socket(serverAddress, serverPort));
            Log.d(DEBUG_TAG, "Connected");

            stateReceiverThread.run();
            movementSenderThread.run();

            if (listener != null) listener.onGameChanged(OnGameStateChangedListener.State.Running);

        } catch (IOException e) {
            e.printStackTrace();
            running = false;
        }

        while (running);

        if (listener != null) listener.onGameChanged(OnGameStateChangedListener.State.Stopping);

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        gameView.setGameState(gameState);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void sendMovementUpdate(MovementUpdate movementUpdate) {
        this.movementUpdate = movementUpdate;
    }

    private class MovementSenderThread implements Runnable {

        private Socket socket;

        private MovementSenderThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream objectOutputStream =
                        new ObjectOutputStream(socket.getOutputStream());

                while (running) {
                    objectOutputStream.writeObject(movementUpdate);
                    objectOutputStream.flush();
                    Thread.sleep(100);
                }
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }

    private class StateReceiverThread implements Runnable {

        private Socket socket;

        private StateReceiverThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream objectInputStream =
                        new ObjectInputStream(socket.getInputStream());

                while (running) {
                    gameState = (GameState) objectInputStream.readObject();
                    publishProgress();
                }
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }
}
