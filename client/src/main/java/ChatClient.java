import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Scanner scanner;
    private MessageSender messageSender;
    private MessageHandler messageHandler;

    public ChatClient() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        try {
            Socket socket = new Socket("localhost", 8080);
            messageSender = new MessageSender(socket.getOutputStream());
            messageHandler = new MessageHandler(socket.getInputStream());

            System.out.println("Введите Ваш ник:\n");
            String username = scanner.next();
            messageSender.send(username);

            new Thread(() -> {

                while (true) {
                    scanner = new Scanner(System.in);
                    String message = scanner.next();
                    messageSender.send(message);
                    System.out.println(message);
                }

            }).start();

            new Thread(() -> {

                while (true) {
                    String message = messageHandler.handle();
                    System.out.println(message);
                }

            }).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new ChatClient().run();
    }
}