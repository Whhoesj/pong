package nl.sjtek.smartmobile.pong.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;


public class MovementThread implements Runnable {

    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;

    private int speed = 0;
    private boolean run = true;

    private UUID uuid;

    public MovementThread(UUID uuid) {
        this.uuid = uuid;
    }

    public void setMovement(int speed) {
        this.speed = speed;
    }


    @Override
    public void run() {
        try {
            socket = new Socket(ConnectionHandler.HOSTNAME, ConnectionHandler.PORT);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer.println(uuid.toString() + ":m");
            if (!reader.readLine().equals("helloMovement!")) run = false;

            while (run) {
                writer.println(Integer.toString(speed));
                if (reader.readLine().equals("close")) run = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) { }
    }
}