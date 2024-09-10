package senai;

import senai.service.impl.ClientHandlerServiceImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServet() throws IOException {
        try {
            System.out.println("Server running...");

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                String clientAddress = socket.getInetAddress().getHostAddress();
                System.out.println("A new cliente has connected: IP Andress - " + clientAddress);
                ClientHandlerServiceImpl clientHandler = new ClientHandlerServiceImpl(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            serverSocket.close();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServet();
    }
}