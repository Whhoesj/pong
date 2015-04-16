package nl.sjtek.smartmobile.pong.server;

import nl.sjtek.smartmobile.pong.data.GameUpdate;
import nl.sjtek.smartmobile.pong.data.MovementUpdate;

import java.net.Socket;
import java.util.UUID;


public class Client {

    private UUID uuid;
    private Socket socketMovement;
    private Socket socketUpdate;
    private MovementThread movementThread;
    private UpdateThread updateThread;
    private boolean ready = false;

    public Client() {
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setUuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        setUuid(uuid);
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
        ready = true;
        movementThread = new MovementThread();
        movementThread.run();
        updateThread = new UpdateThread();
        updateThread.run();
        System.out.println("Client " + getUuid() + " ready.");
    }
}
