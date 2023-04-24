import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true){
                MessageHandler handler = new MessageHandler(socket.getInputStream());

                String message = handler.handle();
                System.out.println("Сервер получил сообщение: " + message);

                MessageSender sender = new MessageSender(socket.getOutputStream());
                String newMessage = "OK" + message;
                sender.send(newMessage);
                System.out.println("Сервер отправил сообщение: " + newMessage);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
