package senai;

import senai.model.ClientModel;
import senai.service.impl.ClientServiceImpl;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String messageEnterUserName = "Enter your username for the group chat:";
        System.out.print(messageEnterUserName);
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 1234);
        ClientModel clientModel = new ClientModel(username);
        ClientServiceImpl client = new ClientServiceImpl(socket, clientModel);
        client.listenForMessage();
        client.sendMessage();
    }
}
