package ClientAppTests;

import java.io.IOException;
import java.net.ServerSocket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import za.co.wethinkcode.robotworlds.Client.ClientApp.ClientApp;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientConstructorTest {
    private final int portNumber = 5000;
    private ServerSocket serverSocket;

    @BeforeEach
    void setupMockServer() throws IOException {
        serverSocket = new ServerSocket(portNumber);
        // Start a new thread to keep the server socket open
        new Thread(() -> {
            try {
                serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
        }).start();
    }

    @Test
    void clientConstructorShouldCreateClientSocket() {
        try {
            ClientApp client = new ClientApp("localhost", this.portNumber);
            assertTrue(client.isConnected);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing server socket: " + e.getMessage());
            }
        }
    }
}
