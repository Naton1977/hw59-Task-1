package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client3 {
    public static void main(String[] args) {
        Thread myThead1 = new Thread(new GetMessage3());
        Thread myThread2 = new Thread(new SendMessage3());
        myThread2.start();
        myThead1.start();
    }

}

class SendMessage3 implements Runnable {

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
                outputStream.println("Client3&&" + message);
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
            outputStream.println("IntroduceMySelf&Client3&14044");
            outputStream.flush();
            String count = inputStream.readLine();
            System.out.println("В чате : " + count);
            outputStream.close();
        } catch (Exception e) {

        }
    }
}


class GetMessage3 implements Runnable {
    @Override
    public void run() {
        while (true) {

            try {
                String input;
                ServerSocket server = new ServerSocket();
                server.bind(new InetSocketAddress(InetAddress.getLocalHost(), 14044));
                Socket remoteClient = server.accept();
                BufferedReader inputStream = new BufferedReader(new InputStreamReader(remoteClient.getInputStream()));
                input = inputStream.readLine();
                System.out.println(input);
            } catch (IOException e) {

            }
        }
    }
}
