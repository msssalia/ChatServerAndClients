import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);

            new Thread(() -> {

               while (true){
                   Scanner scanner = new Scanner(System.in);
                   String message = scanner.next();
                   try {
                       MessageSender sender  = new MessageSender(socket.getOutputStream());
                       sender.send(message);
                       System.out.println("Клиент отправил сообщение: " + message);
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               }

            }).start();

            new Thread(() -> {

                while (true){
                    try {
                        MessageHandler  handler = new MessageHandler(socket.getInputStream());
                        String message = handler.handle();
                        System.out.println("Клиент получил сообщение: " + message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

            }).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}