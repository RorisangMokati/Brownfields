package za.co.wethinkcode.robotworlds.Client.ClientApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.Client.Robot.DefenderRobot;
import za.co.wethinkcode.robotworlds.Client.Robot.KillerRobot;
import za.co.wethinkcode.robotworlds.Client.Robot.SniperRobot;
import za.co.wethinkcode.robotworlds.Client.WriteToSocket;
import za.co.wethinkcode.robotworlds.Client.jsonUtility.JsonClient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {

    public boolean isConnected = false;
    public volatile boolean isValidLaunch = true;
    private Socket clientSocket;
    private WriteToSocket writeToSocket;
    private BufferedReader serverInputReader;
    private BufferedWriter clientWriterToServer;
    private final Scanner scanInput = new Scanner(System.in);

    /**
     * Constructs a ClientApp object.
     * @param IP the IP address to connect to
     * @param PORT the port number to connect to
     */
    public ClientApp(String IP, int PORT) {
        createAndConnectClientSocket(IP, PORT);
    }

    /**
     * Initializes and connects the client socket.
     * @param ipAddress  the IP address to connect to
     * @param portNumber the port number to connect to
     */
    public void createAndConnectClientSocket(String ipAddress, int portNumber) {
        try {
            this.clientSocket = new Socket(ipAddress, portNumber);
            this.clientWriterToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.serverInputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writeToSocket = new WriteToSocket();
            this.isConnected = true;
        } catch (IOException e) {
            isConnected = false;
            System.out.println("Error " + e.getMessage());
            System.exit(portNumber);
        }
    }

    /**
     * Launches the client application, prompting the user to input launch commands.
     */
    public void clientLaunch() {
        System.out.print("Welcome to Robot worlds!\n It's time to launch your robot : ");
        try {
            launchInput();
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    /**
     * Handles the user input for launching the robot.
     *
     * @throws IOException if an I/O error occurs
     */
    public void launchInput() throws IOException {
        String userInput = this.scanInput.nextLine();
        String[] stringList = userInput.split(" ");

        while (true) {
            if (stringList.length == 3) {
                writeToSocket.setCommand(stringList[0]);
                this.writeToSocket.setRobot(stringList[2]);
                setSpecificRobotMake(stringList[1]);
                break;
            }else {
                System.out.println("Expected: launch <make> <name>");
            }
            userInput = this.scanInput.nextLine();
            stringList = userInput.split(" ");
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to send
     * @throws IOException if an I/O error occurs
     */
    public void sendToServer(String message) throws IOException {
        clientWriterToServer.write(message);
        clientWriterToServer.newLine();
        clientWriterToServer.flush();
        writeToSocket.clearArgument();
    }

    /**
     * Sets the robot type by configuring the shield strength and max shots based on the robot make.
     * Prepares the JSON message with robot details and sends it to the server.
     *
     * @param robotMake the make of the robot
     * @throws IOException if an I/O error occurs
     */
    public void setSpecificRobotMake(String robotMake) throws IOException {
        int maxShots = 0;
        int shieldStrength = 0;

        // Set robot attributes based on its type
        switch (robotMake) {
            case "sniper":
                SniperRobot sniperRobot = new SniperRobot();
                shieldStrength = sniperRobot.getShieldStrength();
                maxShots = sniperRobot.getMaxShots();
                break;
            case "killer":
                KillerRobot killerRobot = new KillerRobot();
                shieldStrength = killerRobot.getShieldStrength();
                maxShots = killerRobot.getMaxShots();
                break;
            case "defender":
                DefenderRobot defenderRobot = new DefenderRobot();
                shieldStrength = defenderRobot.getShieldStrength();
                maxShots = defenderRobot.getMaxShots();
                break;
        }

        writeToSocket.setArguments(robotMake);
        writeToSocket.setArguments(String.valueOf(maxShots));
        writeToSocket.setArguments(String.valueOf(shieldStrength));

        JsonNode node = JsonClient.toJson(writeToSocket);
        // Send JSON message to the server
        sendToServer(JsonClient.stringify(node));
    }

    /**
     * Listens for user input, sets the robot type, and continuously sends messages to the server.
     * Handles different commands and sends them as JSON messages to the server.
     */
    public void userMessageToServer() {
        try {
            // Continuously listen for user input
            while (clientSocket.isConnected()) {

                if (!isValidLaunch) {
                    try {
                        isValidLaunch = true;
                        launchInput();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    continue;
                }

                String command = this.scanInput.nextLine();
                String[] commands = command.split(" ", 2);

                writeToSocket.setCommand(commands[0]);

//                if () {
//                    System.out.println("Until we see next time.");
//                    System.exit(1);
//                    break;
//                }

                if (commands.length > 1){
                    String arg = commands[1];
                    String[] splitArg = arg.split(" ");
                    for(String arguments : splitArg){
                        writeToSocket.setArguments(arguments);
                    }
                } else {
                    if (command.contains("state") || command.contains("look") || command.contains("reload") ||
                            command.contains("repair") || command.contains("fire")) {
                        writeToSocket.setArguments(" ");
                    } else if (command.equals("quit")) {
                        disconnect();
                    }
                    else {
                        System.out.println("Expected arguements for the command");
                    }
                }

                JsonNode node = JsonClient.toJson(writeToSocket);
                // Send JSON message to the server
                sendToServer(JsonClient.stringify(node));
            }
        } catch (IOException e) {
            disconnect();

        }
    }

//    public void validateLaunch(boolean isValidLaunch) {
//        this.isValidLaunch = isValidLaunch;
//    }

    /**
     * Listens for messages from the server in a separate thread and prints them to the console.
     * If the connection is closed, the program is terminated.
     */
    public void listeningForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgRunGame;
                while (clientSocket.isConnected()) {
                    try {
                        msgRunGame = serverInputReader.readLine();
                        if (msgRunGame == null) {
                            // Handle server disconnection
                            disconnect();
                            System.out.println("\nGame Ended\nShutting down...");
                            System.exit(1);
                            break;
                        } else {
                            System.out.println(msgRunGame);
                            if (msgRunGame.contains("has left the world")) {
                                disconnect();
                            }
                            isValidLaunch = !msgRunGame.contains("Too many of you in this world");
                        }
                    } catch (IOException e) {
                        disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * Disconnects the client by closing the socket and associated streams.
     */
    public void disconnect() {
        writeToSocket.setCommand("quit");
        JsonNode node = JsonClient.toJson(writeToSocket);
        writeToSocket.setArguments(" ");
        try {
            sendToServer(JsonClient.stringify(node));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        closeProgram(this.clientSocket, this.serverInputReader, this.clientWriterToServer);
        System.exit(1);
    }

    /**
     * Closes the client socket and associated streams.
     *
     * @param clientSocket       the client socket to close
     * @param serverInputReader  the BufferedReader to close
     * @param clientWriterToServer the BufferedWriter to close
     */
    public void closeProgram(Socket clientSocket, BufferedReader serverInputReader, BufferedWriter clientWriterToServer) {
        try {
            if (serverInputReader != null) {
                serverInputReader.close();
            }
            if (clientWriterToServer != null) {
                clientWriterToServer.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    /**
     * The main method to start the client application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        ClientApp robot = new ClientApp("localhost", 5000);
        robot.clientLaunch();
        robot.listeningForMessage();
        robot.userMessageToServer();
    }
}