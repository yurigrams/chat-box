package senai.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public interface IClientHandlerService {
    void broadcastMessage(String messageToSend);
    void removeClientHandler();
    void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter);
}
