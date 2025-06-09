package SocketTest;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SocketTest {

    public static ServerSocket createServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }

    @Test
    public void testServerSocket() throws IOException {
        final int port = 1234;
        try (ServerSocket serverSocket = createServerSocket(port)) {
            assertEquals(port, serverSocket.getLocalPort());
        }
    }
}
