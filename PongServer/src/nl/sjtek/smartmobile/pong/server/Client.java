package nl.sjtek.smartmobile.pong.server;

import nl.sjtek.smartmobile.pong.data.GameUpdate;
import nl.sjtek.smartmobile.pong.data.MovementUpdate;

import java.net.Socket;
import java.util.UUID;


public class Client {

    public static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private UUID uuid = EMPTY_UUID;
    private Socket socketMovement;
    private Socket socketUpdate;
    private MovementThread movementThread;
    private UpdateThread updateThread;
    private boolean ready = false;

    public Client() {
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
        System.out.println("UUID set to: " + uuid.toString());
    }

    public void setSocketMovement(Socket socketMovement) {
        this.socketMovement = socketMovement;
        if (socketUpdate != null) setReady();
    }

    public void setSocketUpdate(Socket socketUpdate) {
        this.socketUpdate = socketUpdate;
        if (socketMovement != null) setReady();
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isReady() {
        return ready;
    }

    public MovementUpdate getMovementUpdate() {
        return movementThread.getMovementUpdate();
    }

    public void setGameUpdate(GameUpdate gameUpdate) {
        updateThread.setUpdate(gameUpdate);
    }

    private void setReady() {
        System.out.println("Client " + getUuid() + " starting...");
        ready = true;
        movementThread = new MovementThread(socketMovement);
        movementThread.run();
        updateThread = new UpdateThread(socketUpdate);
        updateThread.run();
        System.out.println("Client " + getUuid() + " ready.");
    }

    @Override
    public String toString() {
        return "Client{" +
                "uuid=" + uuid +
                ", socketMovement=" + ((socketMovement == null) ? "null" : "not null") +
                ", socketUpdate=" + ((socketUpdate == null) ? "null" : "not null") +
                ", ready=" + ready +
                '}';
    }
}
