package org.example;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

public class Worker implements Runnable {
    Socket clientSocket;
    String clientMessage;

    Worker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintStream outputStream = new PrintStream(clientSocket.getOutputStream());
            clientMessage = inputStream.readLine();
            int introduceMySelfIndex1 = clientMessage.indexOf("&");
            if (introduceMySelfIndex1 == 15) {
                String introduceMySelfIndex = clientMessage.substring(0, 15);
                if (introduceMySelfIndex.equals("IntroduceMySelf")) {
                    int clientNumberIndex = clientMessage.indexOf("&", 16);
                    String client = clientMessage.substring(16, clientNumberIndex);
                    String clientPort = clientMessage.substring((clientNumberIndex + 1), clientMessage.length());
                    int clientPortInt = Integer.parseInt(clientPort);
                    if (Server.clients.size() == 0) {
                        Server.clients.put(client, clientPortInt);
                        String num = Integer.toString(Server.clients.size());
                        outputStream.println(num);
                    } else {
                        Server.clients.put(client, clientPortInt);
                        String num = Integer.toString(Server.clients.size());
                        outputStream.println(num);

                        for (Map.Entry<String, Integer> entry : Server.clients.entrySet()) {
                            if (!entry.getKey().equals(client)) {
                                Socket clientPort2 = new Socket();
                                int soc = entry.getValue();
                                clientPort2.connect(new InetSocketAddress(InetAddress.getLocalHost(), soc));
                                PrintStream outputStreamClient = new PrintStream(clientPort2.getOutputStream());
                                outputStreamClient.println("В чате : " + Server.clients.size());
                                outputStreamClient.flush();
                            }
                        }
                    }
                }
            } else {
                int clientName = clientMessage.indexOf("&&");
                String client = clientMessage.substring(0, clientName);
                String message = clientMessage.substring((clientName + 2), clientMessage.length());
                for (Map.Entry<String, Integer> entry : Server.clients.entrySet()) {
                    if (!entry.getKey().equals(client)) {
                        Socket clientPort = new Socket();
                        int soc = entry.getValue();
                        try {
                            clientPort.connect(new InetSocketAddress(InetAddress.getLocalHost(), soc));
                            PrintStream outputStreamClient = new PrintStream(clientPort.getOutputStream());
                            outputStreamClient.println("Пользователь " + client + " " + "написал : " + message);
                            outputStreamClient.flush();
                        } catch (Exception e) {

                        }
                    }
                }
            }

        } catch (Exception e) {

        }
    }
}

