package nl.sjtek.smartmobile.pong.server;

import nl.sjtek.smartmobile.pong.data.MovementUpdate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by wouter on 23-3-15.
 */
public class MovementThread implements Runnable {

    private Socket socket;
    private MovementUpdate update;

    public MovementUpdate getMovementUpdate() {
        return update;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            while (true) {
                Object receivedObject = inputStream.readObject();
                update = (MovementUpdate)receivedObject;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}