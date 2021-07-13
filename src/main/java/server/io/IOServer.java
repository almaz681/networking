package server.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class IOServer {

    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(200);
        ServerSocket serverSocket = new ServerSocket(45000);
        log("Server started at port 45000. Listening for client connections...");

        try {
            while (true) {
                //Blocking call, never null
                final Socket socket = serverSocket.accept();
//                handle(socket); // Handle in the same thread
//                new Thread(() -> handle(socket)).start(); //Handle in always new thread
                pool.submit(() -> handle(socket)); // Handle in thead pool
            }
        } finally {
            pool.shutdown();
            serverSocket.close();
        }
    }

    private static void handle(Socket socket) {
        log("Client connected: " + socket.getRemoteSocketAddress());
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // Receive message from the client
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String clientRequest = reader.readLine(); //Blocking call

            log("Receive from " + socket.getRemoteSocketAddress() + ">" + clientRequest);

            // Send response
            String serverResponse = clientRequest + ", serverTime=" + System.currentTimeMillis();
            PrintWriter writer = new PrintWriter(out);
            writer.println(serverResponse);
            writer.flush();

            log("Send to " + socket.getRemoteSocketAddress() + ">" + serverResponse);
        } catch (IOException e) {
            log("Error " + e.getMessage());
        }
    }

    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }
}
