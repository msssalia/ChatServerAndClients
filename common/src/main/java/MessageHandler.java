import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MessageHandler {

    private final DataInputStream in;

    public MessageHandler(InputStream inputStream) {
        in = new DataInputStream(new BufferedInputStream(inputStream));
    }

    public String handle(){
        int length = 0;
        try {
            length = in.readInt();
            byte[] bytes = new byte[length];

            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = in.readByte();
            }

            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
