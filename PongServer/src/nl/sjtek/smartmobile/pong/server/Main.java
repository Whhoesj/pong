package nl.sjtek.smartmobile.pong.server;

import java.io.IOException;

/**
 * Created by wouter on 16-4-15.
 */
public class Main {
    public static void main(String[] args) {
        try {
            PongServer pongServer = new PongServer();
            pongServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
