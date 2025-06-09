package ClientAppTests;

import java.io.IOException;
import java.net.ServerSocket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import za.co.wethinkcode.robotworlds.Client.ClientApp.ClientApp;

public class CreateAndConnectClientSocketTest {
    private final int portNumber = 5000;
    private ServerSocket mockServerSocket;
    private ClientApp client;

    @BeforeEach
    void setupMockServer() throws IOException {
        mockServerSocket = new ServerSocket(portNumber);
        // Start a new thread to keep the server socket open
        new Thread(() -> {
            try {
                System.out.println("Server socket accepting connections on port " + portNumber);
                mockServerSocket.accept();
            } catch (IOException e) {
                System.out.println("Error in server socket thread: " + e.getMessage());
            }
        }).start();
    }

    // @Test
    // void createAndConnectClientSocketShouldConnectToServer() {
    //     try {
    //         client = new ClientApp("localhost", portNumber);
    //         client.createAndConnectClientSocket("localhost", portNumber);
    //         assertTrue(client.isConnected);
    //     } finally {
    //         try {
    //             mockServerSocket.close();
    //         } catch (IOException e) {
    //             System.out.println("Error closing server socket: " + e.getMessage());
    //         }
    //     }
    // }

//    @Test
//    void createAndConnectClientSocketShouldNotConnect() throws IOException {
//        client = new ClientApp("localhost", 9999); // Use an invalid port
//        client.createAndConnectClientSocket("localhost", 9999);
//        assertFalse(client.isConnected);
//    }
}