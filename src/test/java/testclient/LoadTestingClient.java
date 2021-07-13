package testclient;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LoadTestingClient {

    public static void main(String[] args) {
        List<Socket> sockets = new ArrayList<>();
        System.out.println("Openning sockets");
        for (int i = 0; i < 6_000; i++) {
            try {
                sockets.add(new Socket("localhost", 45001));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Print any string to exit");
        new Scanner(System.in).next();

        // Closing connections
        System.out.println("Closing connections");
        for (Socket socket : sockets) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
