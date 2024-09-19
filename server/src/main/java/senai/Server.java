package senai;

import senai.service.ClientHandlerService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServet(int port) throws IOException {
        try {
            System.out.println("Server started on port " + port);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new cliente has connected");
                ClientHandlerService clientHandler = new ClientHandlerService(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            serverSocket.close();
            e.printStackTrace();
        }
    }

    public static int getPort() {
        Scanner scanner = new Scanner(System.in);
        int port = -1;
        while (port < 1024 || port > 65535) {
            System.out.println("Please enter a server port (1024-65535):");
            try {
                port = Integer.parseInt(scanner.nextLine());
                if (port < 1024 || port > 65535) {
                    System.out.println("Port number out of range. Please enter a value between 1024 and 65535.");
                    port = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for the port.");
            }
        }
        return port;
    }

    public static void main(String[] args) throws IOException {
        int port = getPort();
        ServerSocket serverSocket = new ServerSocket(port);
        Server server = new Server(serverSocket);
        server.startServet(port);
    }
}
