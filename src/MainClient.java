import java.io.IOException;

public class MainClient {
    public static void main(String args[]) throws IOException {
        Client client = new Client();
        client.start("localhost", 8888);
    }
}

