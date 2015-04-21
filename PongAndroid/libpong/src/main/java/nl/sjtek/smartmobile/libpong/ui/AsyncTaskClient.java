package nl.sjtek.smartmobile.libpong.ui;

import android.os.AsyncTask;

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

    private final String serverAddress;
    private final int serverPort;

    private GameState gameState;
    private MovementUpdate movementUpdate;
    private boolean running = true;

    private OnGameChangedListener listener;

    /**
     * Setup the {@link android.os.AsyncTask}.
     * @param serverAddress The address to connect to
     * @param serverPort The port to connect to
     */
    public AsyncTaskClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        StateReceiverThread stateReceiverThread;
        MovementSenderThread movementSenderThread;

        try {
            if (listener != null) listener.onGameChanged(OnGameChangedListener.State.Connecting);
            stateReceiverThread = new StateReceiverThread(
                    new Socket(serverAddress, serverPort));
            movementSenderThread = new MovementSenderThread(
                    new Socket(serverAddress, serverPort));

            stateReceiverThread.run();
            movementSenderThread.run();

            if (listener != null) listener.onGameChanged(OnGameChangedListener.State.Running);

        } catch (IOException e) {
            e.printStackTrace();
            running = false;
        }

        while (running);

        if (listener != null) listener.onGameChanged(OnGameChangedListener.State.Stopping);

        return null;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void sendMovementUpdate(MovementUpdate movementUpdate) {
        this.movementUpdate = movementUpdate;
    }

    public void setListener(OnGameChangedListener listener) {
        this.listener = listener;
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
