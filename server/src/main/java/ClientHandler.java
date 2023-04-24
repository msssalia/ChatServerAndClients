import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Vector<ClientHandler> clientHandlers;
    private final MessageHandler messageHandler;
    private final MessageSender messageSender;
    private String username;

    public ClientHandler(Socket socket, Vector<ClientHandler> clientHandlers) throws IOException {
        this.socket = socket;
        this.clientHandlers = clientHandlers;
        this.messageHandler = new MessageHandler(socket.getInputStream());
        this.messageSender = new MessageSender(socket.getOutputStream());
    }

    @Override
    public void run() {
        username = messageHandler.handle();

        while (true) {
            String message = messageHandler.handle();
            System.out.println("Сервер получил сообщение: " + message);

            String[] args = message.split("\\$");
            String username = args[0];
            String text = args[1];

            boolean isSend = false;

            for (ClientHandler client : clientHandlers) {
                if (client.getUsername() != null && client.getUsername().equals(username)) {
                    client.sendMessage(getUsername(), text);
                    isSend = true;
                }
            }

            if (!isSend){
                sendMessage(username, "Not found!");
            }
        }
    }

    public String getUsername() {
        return this.username;
    }

    public void sendMessage(String username, String text) {
        sendMessage("  [" + username + "] " + text);
    }

    public void sendMessage(String message) {
        messageSender.send(message);
        System.out.println("Сервер отправил сообщение: " + message + " | Username: " + this.username);
    }
}
