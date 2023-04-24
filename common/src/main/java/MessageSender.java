import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MessageSender {

    private final DataOutputStream out;

    public MessageSender(OutputStream outputStream) {
        out = new DataOutputStream(outputStream);
    }

    public void send(String message) {
        try {
            out.writeInt(message.length());
            out.write(message.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
