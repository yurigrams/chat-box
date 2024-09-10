package senai.service.impl;

import senai.model.ClientModel;
import senai.service.IClientService;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientServiceImpl implements IClientService {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ClientModel clientModel;

    public ClientServiceImpl(Socket socket, ClientModel clientModel) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientModel = clientModel;
        } catch (IOException e) {
            closeEverything();
        }
    }

    @Override
    public void sendMessage() {
        try {
            bufferedWriter.write(clientModel.getUsername());
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);

            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(clientModel.getUsername() + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything();
        }
    }

    @Override
    public void listenForMessage() {
        new Thread(() -> {
            String msgFromGroupChat;
            while (socket.isConnected()) {
                try {
                    msgFromGroupChat = bufferedReader.readLine();
                    System.out.println(msgFromGroupChat);
                } catch (IOException e) {
                    closeEverything();
                }
            }
        }).start();
    }

    @Override
    public void closeEverything() {
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
