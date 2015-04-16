package nl.sjtek.smartmobile.pong.data;

import java.util.UUID;

/**
 * Created by wouter on 16-4-15.
 */
public class ClientUpdate {

    private UUID uuid;

    public ClientUpdate(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
