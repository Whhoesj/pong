package nl.sjtek.smartmobile.pong.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import nl.sjtek.smartmobile.pong.data.GameUpdate;

public class UpdateThread implements Runnable {

    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private Socket socket;

    private GameUpdate gameUpdate = null;
    private boolean run = true;

    private UUID uuid;

    public UpdateThread(UUID uuid) {
        this.uuid = uuid;
    }

    public GameUpdate getGameUpdate() {
        return gameUpdate;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(ConnectionHandler.HOSTNAME, ConnectionHandler.PORT);
            writer = new ObjectOutputStream(socket.getOutputStream());
            reader = new ObjectInputStream(socket.getInputStream());

            writer.writeObject(uuid.toString() + ":u\n");
            if (!reader.readObject().equals("helloUpdate!")) run = false;

            while (run) {
                gameUpdate = (GameUpdate) reader.readObject();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) { }
    }
}