package nl.sjtek.smartmobile.pong.data;

import java.io.Serializable;
import java.util.UUID;


public class MovementUpdate implements Serializable {

    private final UUID uuid;
    private float movementValue;

    public MovementUpdate(UUID uuid, float movementValue) {
        this.uuid = uuid;
        this.movementValue = movementValue;
    }

    public UUID getUuid() {
        return uuid;
    }

    public float getMovementValue() {
        return movementValue;
    }
}
