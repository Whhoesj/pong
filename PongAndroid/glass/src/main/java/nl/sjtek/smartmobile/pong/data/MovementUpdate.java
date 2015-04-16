package nl.sjtek.smartmobile.pong.data;

import java.util.UUID;


public class MovementUpdate extends ClientUpdate {

    private float movementValue;

    public MovementUpdate(UUID uuid, float movementValue) {
        super(uuid);
        this.movementValue = movementValue;
    }

    public float getMovementValue() {
        return movementValue;
    }
}
