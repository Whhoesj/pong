package nl.sjtek.smartmobile.pong.client;

import java.util.UUID;

import nl.sjtek.smartmobile.pong.data.GameUpdate;

public class ConnectionHandler {

    public static final String HOSTNAME = "bienske.wouterhabets.com";
    public static final int PORT = 1337;

    private MovementThread movementThread;
    private UpdateThread updateThread;

    private UUID uuid = UUID.randomUUID();

    public ConnectionHandler() {
        movementThread = new MovementThread(uuid);
        updateThread = new UpdateThread(uuid);
    }

    public void start() {
        movementThread.run();
        updateThread.run();
    }

    public void stop() {

    }

    public void sendSpeed(int speed) {
        movementThread.setMovement(speed);
    }

    public GameUpdate getUpdate() {
        return updateThread.getGameUpdate();
    }

}