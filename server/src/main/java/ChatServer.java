import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {

    private final Vector<ClientHandler> clientHandlers;

    public ChatServer() {
        this.clientHandlers = new Vector<>();
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Запуск Сервера");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключен");
                ClientHandler clientHandler = new ClientHandler(socket, clientHandlers);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public static void main(String[] args) {
        new ChatServer().run();
    }
}
