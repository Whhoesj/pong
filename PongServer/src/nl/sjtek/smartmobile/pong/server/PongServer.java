package nl.sjtek.smartmobile.pong.server;

import nl.sjtek.smartmobile.pong.data.GameUpdate;
import nl.sjtek.smartmobile.pong.data.MovementUpdate;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class PongServer {

    private static final int PORT = 1337;
    private ServerSocket serverSocket;

    private Client client1;
    private Client client2;

    public PongServer() throws IOException, ClassNotFoundException {
        System.out.println("Starting Pong server on port " + PORT + "...");
        serverSocket = new ServerSocket(PORT);
        setupClients();
    }

    private void setupClients() throws IOException, ClassNotFoundException {
        client1 = new Client();
        client2 = new Client();
        System.out.println("Waiting for incoming connections...");
        while (!client1.isReady() && !client2.isReady()) {
            System.out.println(client1.toString());
            System.out.println(client2.toString());
            Socket socket = serverSocket.accept();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Object receivedObject = (Object)inputStream.readObject();

            if (receivedObject instanceof GameUpdate) {

                GameUpdate receivedGameUpdate = (GameUpdate) receivedObject;
                UUID uuid = receivedGameUpdate.getUuid();

                if (client1.getUuid().compareTo(uuid) == 0) {
                    client1.setSocketUpdate(socket);
                    System.out.println("    Client: " + uuid + " - GameUpdate.");
                } else if (client2.getUuid().compareTo(uuid) == 0) {
                    client2.setSocketUpdate(socket);
                    System.out.println("    Client: " + uuid + " - GameUpdate.");
                } else if (client1.getUuid().compareTo(Client.EMPTY_UUID) == 0) {
                    client1.setUuid(uuid);
                    client1.setSocketUpdate(socket);
                    System.out.println("New client: " + uuid + " - GameUpdate.");
                } else if (client2.getUuid().compareTo(Client.EMPTY_UUID) == 0) {
                    client2.setUuid(uuid);
                    client2.setSocketUpdate(socket);
                    System.out.println("New client: " + uuid + " - GameUpdate.");
                }

            } else if (receivedObject instanceof MovementUpdate) {

                MovementUpdate movementUpdate = (MovementUpdate) receivedObject;
                UUID uuid = movementUpdate.getUuid();

                if (client1.getUuid().compareTo(uuid) == 0) {
                    client1.setSocketMovement(socket);
                    System.out.println("    Client: " + uuid + " - MovementUpdate.");
                } else if (client2.getUuid().compareTo(uuid) == 0) {
                    client2.setSocketMovement(socket);
                    System.out.println("    Client: " + uuid + " - MovementUpdate.");
                } else if (client1.getUuid().compareTo(Client.EMPTY_UUID) == 0) {
                    client1.setUuid(uuid);
                    client1.setSocketMovement(socket);
                    System.out.println("New client: " + uuid + " - MovementUpdate.");
                } else if (client2.getUuid().compareTo(Client.EMPTY_UUID) == 0) {
                    client2.setUuid(uuid);
                    client2.setSocketMovement(socket);
                    System.out.println("New client: " + uuid + " - MovementUpdate.");
                }

            }

            inputStream.close();
        }

        System.out.println("Ready to start.");
    }

    public void run() {
        GameState gameState = new GameState();
        while (true) {
            gameState.setTopBat(client1.getMovementUpdate());
            gameState.setBottomBat(client2.getMovementUpdate());
            gameState.update();
            GameUpdate gameUpdate = gameState.getUpdate();
            client1.setGameUpdate(gameUpdate);
            client2.setGameUpdate(gameUpdate);

        }
    }
}
