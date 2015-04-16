package nl.sjtek.smartmobile.pong.server;

import nl.sjtek.smartmobile.pong.data.GameUpdate;
import nl.sjtek.smartmobile.pong.data.MovementUpdate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by wouter on 23-3-15.
 */
public class UpdateThread implements Runnable {

    private Socket socket;
    private GameUpdate update;

    public UpdateThread(Socket socket) {
        this.socket = socket;
    }

    public void setUpdate(GameUpdate update) {
        this.update = update;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            while (true) {
                outputStream.writeObject(update);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
