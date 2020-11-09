package org.example;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) throws IOException {
        Thread myThead1 = new Thread(new GetMessage());
        Thread myThread2 = new Thread(new SendMessage());
        myThread2.start();
        myThead1.start();

    }

}

class SendMessage implements Runnable {

    @Override
    public void run() {
        introduceMyself();
        System.out.println("Введите Ваше сообщение");
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                String message;
                message = scanner.nextLine();
                Socket client = new Socket();
                client.connect(new InetSocketAddress(InetAddress.getLocalHost(), 10_000));
                PrintStream outputStream = new PrintStream(client.getOutputStream());
                outputStream.println("Client1&&" + message);
                client.close();
            } catch (IOException e) {

            }
        }
    }

    public void introduceMyself() {

        try {
            Socket client = new Socket();
            client.connect(new InetSocketAddress(InetAddress.getLocalHost(), 10000));
            PrintStream outputStream = new PrintStream(client.getOutputStream());
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outputStream.println("IntroduceMySelf&Client1&12032");
            outputStream.flush();
            String count = inputStream.readLine();
            System.out.println("В чате : " + count);
            outputStream.close();
        } catch (Exception e) {

        }
    }
}


class GetMessage implements Runnable {
    @Override
    public void run() {
        String input;
        ServerSocket server = null;
        try {
            server = new ServerSocket();
        } catch (IOException e) {
        }
        try {
            server.bind(new InetSocketAddress(InetAddress.getLocalHost(), 12032));
        } catch (IOException e) {
        }
        while (true) {

            try {
                Socket remoteClient = server.accept();
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(remoteClient.getInputStream()));
                input = inputStream.readLine();
                System.out.println(input);
            } catch (IOException e) {

            }
        }
    }
}



















