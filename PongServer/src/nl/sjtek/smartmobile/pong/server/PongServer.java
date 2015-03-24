package nl.sjtek.smartmobile.pong.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;

public class PongServer {

    private static final int PORT = 1337;
    private ServerSocket serverSocket;

    private Client client1;
    private Client client2;

    public PongServer() throws IOException {
        serverSocket = new ServerSocket(PORT);
    }

    public void start() {
        try {
            client1 = new Client();
            client2 = new Client();
            while (!client1.isReady() && !client2.isReady()) {
                Socket socket = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputString = reader.readLine();
                UUID uuid = UUID.fromString(inputString.split(";")[0]);
                if (checkSocket(inputString, socket)) {
                } else {
                    if (client1.getUuid() == null) {
                        client1.setUuid(uuid);
                    } else if (client2.getUuid() == null) {
                        client2.setUuid(uuid);
                    }
                    checkSocket(inputString, socket);
                }
                reader.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkSocket(String inputString, Socket socket1) {
        UUID uuid = UUID.fromString(inputString.split(";")[0]);
        if (client1.getUuid() == uuid) {
            if (inputString.split(";")[1].contains("m")) {
                client1.setSocketMovement(socket1);
            } else if (inputString.split(";")[1].contains("u")) {
                client1.setSocketUpdate(socket1);
            }
        } else if (client2.getUuid() == uuid) {
            if (inputString.split(";")[1].contains("m")) {
                client2.setSocketMovement(socket1);
            } else if (inputString.split(";")[1].contains("u")) {
                client2.setSocketUpdate(socket1);
            }
        } else {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {

    }
}
