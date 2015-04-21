package nl.sjtek.smartmobile.libpong.ui;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import nl.sjtek.smartmobile.libpong.game.GameState;
import nl.sjtek.smartmobile.libpong.game.MovementUpdate;

/**
 * An {@link android.os.AsyncTask} to host a game of Pong.
 * <p>
 *     This {@link android.os.AsyncTask} will listen for a player on port 1337 when executed.<br>
 *     When running, an {@link nl.sjtek.smartmobile.libpong.game.GameState} can be send with
 *     {@link nl.sjtek.smartmobile.libpong.ui.AsyncTaskHost#sendGameState(nl.sjtek.smartmobile.libpong.game.GameState)}.
 * </p>
 */
public class AsyncTaskHost extends AsyncTask<Void, Void, Void> {

    /**
     * The port the host will listen to.
     */
    public static final int PORT = 1337;

    private boolean running = true;

    private GameState gameState;
    private MovementUpdate movementUpdate;

    private OnGameStateChangedListener listener;

    public AsyncTaskHost(OnGameStateChangedListener listener) {
        this.listener = listener;
    }

    /**
     * Send a new {@link nl.sjtek.smartmobile.libpong.game.GameState} to the client.
     * @param gameState The GameState to send
     */
    public void sendGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Get the latest received {@link nl.sjtek.smartmobile.libpong.game.MovementUpdate}.
     * @return Latest received {@link nl.sjtek.smartmobile.libpong.game.MovementUpdate}
     */
    public MovementUpdate getMovementUpdate() {
        return movementUpdate;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        ServerSocket serverSocket;
        StateUpdaterThread stateUpdaterThread;
        MovementReceiverThread movementReceiverThread;

        try {
            serverSocket = new ServerSocket(PORT);
            stateUpdaterThread = new StateUpdaterThread(serverSocket.accept());
            movementReceiverThread = new MovementReceiverThread(serverSocket.accept());
            stateUpdaterThread.run();
            movementReceiverThread.run();
            if (listener != null) listener.onGameChanged(OnGameStateChangedListener.State.Running);
        } catch (IOException e) {
            running = false;
            e.printStackTrace();
        }


        while (running);

        if (listener != null) listener.onGameChanged(OnGameStateChangedListener.State.Stopping);

        return null;
    }

    private class StateUpdaterThread implements Runnable {

        private final Socket socket;

        private StateUpdaterThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                while (running) {
                    objectOutputStream.writeObject(gameState);
                    Thread.sleep(50);
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

    private class MovementReceiverThread implements Runnable {

        private final Socket socket;

        private MovementReceiverThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                while (running) {
                    movementUpdate = (MovementUpdate) objectInputStream.readObject();
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
