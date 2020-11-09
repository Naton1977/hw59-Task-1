package org.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.*;

public class Server {
    static Map<String, Integer> clients = new TreeMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(10_000));
        Socket remoteClient;
        ExecutorService executor = Executors.newCachedThreadPool();
        while (true) {
            System.out.println("Wait for clients ....");
            remoteClient = serverSocket.accept();
            executor.submit(new Worker(remoteClient));
        }

    }
}











