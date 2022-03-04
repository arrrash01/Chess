import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Client {
    private final String IP = "localhost";
    private final int port = 8080;
    Socket socket = new Socket(IP, port);

    public Client() throws IOException {
    }

    void connectToServer() {
        try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

