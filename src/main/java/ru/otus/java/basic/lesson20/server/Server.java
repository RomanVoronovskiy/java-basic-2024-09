package ru.otus.java.basic.lesson20.server;

import ru.otus.java.basic.lesson20.util.ClientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final List<ClientHandler> clientHandlers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8080);
        System.out.println("SERVER APPLICATION RUN!");
        while (true) {
            Socket client = socket.accept();
            DataInputStream inputStream = new DataInputStream(client.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
            System.out.println("Клиент с портом :" + client.getPort() + " подключился!");
            ClientHandler clientHandler = new ClientHandler(client, inputStream, outputStream);
            clientHandlers.add(clientHandler);
            String userInput = inputStream.readUTF();
            if (userInput.equals("exit")) {
                System.out.println("Клиент с портом :" + client.getPort() + " отключился!");
                client.close();
                continue;
            }
            System.out.println(userInput);
            System.out.println("client.getPort() = " + client.getPort());
            String result = transformToUpperCase(userInput);
            outputStream.writeUTF(result);
            outputStream.flush();
            System.out.println("result = " + result);
        }
    }

    private static String transformToUpperCase(String userInput) {
        System.out.println("выполняем трансформацию!");
        return userInput.toUpperCase();
    }
}
