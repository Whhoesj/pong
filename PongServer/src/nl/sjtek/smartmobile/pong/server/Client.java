package nl.sjtek.smartmobile.pong.server;

import java.net.Socket;
import java.util.UUID;

/**
 * Created by wouter on 23-3-15.
 */
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

    public void setSocketMovement(Socket socketMovement) {
        this.socketMovement = socketMovement;
    }

    public void setSocketUpdate(Socket socketUpdate) {
        this.socketUpdate = socketUpdate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isReady() {
        return ready;
    }
}
