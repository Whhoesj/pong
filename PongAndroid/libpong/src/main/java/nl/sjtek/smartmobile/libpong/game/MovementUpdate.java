package nl.sjtek.smartmobile.libpong.game;

/**
 * Contains an {@link java.lang.Integer} for bat movement.
 * <p>The client send this to the host in multiplayer</p>
 */
public class MovementUpdate {

    private Integer value;

    /**
     * Create a new update object.
     * @param value The value
     */
    public MovementUpdate(Integer value) {
        this.value = value;
    }

    /**
     * Get the update value.
     * @return The value
     */
    public Integer getValue() {
        return value;
    }
}
