package za.co.wethinkcode.robotworlds.Server.RobotServer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.Commands.Command;
import za.co.wethinkcode.robotworlds.Server.Robot.RobotCreator;
import za.co.wethinkcode.robotworlds.Server.World.World;
import za.co.wethinkcode.robotworlds.Server.jsonUtility.JsonServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RobotServer implements Runnable {

    public static ArrayList<RobotServer>  clientRobots = new ArrayList<>();
    public static ArrayList<String> robotNames = new ArrayList<>();
    public static final int PORT = CommandLineArguments.getPort();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private PrintStream out;
    public String robotName;
    public String robotMake;
    private World world;
    private  RobotCreator userRobot;

    public RobotServer(Socket socket, World world){
        try {
            this.socket = socket;
            this.out = new PrintStream(socket.getOutputStream());
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //this.robotName = "testing";
            this.world = world;
//            bufferedWriter.write("Successfully connected to the server " + robotName);
//            bufferedWriter.newLine();
            bufferedWriter.flush();

//            displayOutput("Server: " + robotName.toUpperCase() + " joined the game!");

        } catch (IOException e) {
            closeProgram(socket, bufferedReader, bufferedWriter);
        }
    }
    public static ArrayList<RobotServer> getClientRobots() {
        return clientRobots;
    }
    public static ArrayList<String> getClientsRobots() {
        return robotNames;
    }


    @Override
    public void run() {
        String commandFromRobot;
        List<String> activeRobot = new ArrayList<>();
        boolean isInWorld = true;

        while(socket.isConnected()) {
            try {
                while(true){

                    bufferedWriter.flush();
                    try {
                        List<String> argList = new ArrayList<>();
                        commandFromRobot = bufferedReader.readLine();
                        if (commandFromRobot == null && isInWorld) {
                            argList.add(" ");
                            Command.create("quit", argList, this.userRobot, this.robotName, this.robotMake);
                            System.out.println("Client disconnected.");
                            isInWorld = false;
                            break;
                        }
                        JsonNode node = JsonServer.parse(commandFromRobot);
                        System.out.println(JsonServer.prettyPrint(node));

                        String command = node.get("command").asText();
                        activeRobot.add(node.get("command").asText());


                        if(node.get("arguments")!= null && node.get("arguments").isArray()){
                            ArrayNode arrayNode = (ArrayNode) node.get("arguments");
                            for (JsonNode nodes : arrayNode){
                                argList.add(nodes.textValue());
                            }

                            bufferedWriter.flush();

                            if (commandFromRobot.contains("launch") && clientRobots.size() <= World.worldCapacity) {
                                clientRobots.add(this);
                                if (clientRobots.size() > World.worldCapacity) {
                                    this.robotName = node.get("robot").asText();
                                    this.robotMake = node.get("arguments").get(0).toString();
                                    Command.create(command, argList, null,  this.robotName, this.robotName);
                                    break;
                                }
                                this.robotMake = node.get("arguments").get(0).toString();
                                this.robotName = node.get("robot").asText();
                                this.userRobot = new RobotCreator(this.world, this.robotName, this.robotMake);
                            }

                            Command usercommand = Command.create(command, argList, this.userRobot, this.robotName, this.robotMake);
                            if (commandFromRobot.contains("quit")) {
                                removeRobot();
                            }
                            assert usercommand != null;
                            JSONObject response = usercommand.response;
                            out.println(response);
                        }
                        if (this.userRobot != null){
                            robotNames.add(this.userRobot.getName());}

                    }
                    catch (IOException e) {
                        System.out.println(robotName.toUpperCase() + " has left the world...");
                        assert this.userRobot.getRobot() != null;
                        this.world.getRobotsInWorld().remove(this.userRobot.getRobot());
                        closeProgram(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                    catch (IllegalArgumentException e){
                        // handling argument exception
                    }
                }
            }catch (IOException e){
                break;
            }
        }
    }

    public void displayOutput(String commandsToServe){
        for(RobotServer robot: clientRobots){
            try {
                if (robot.robotName.equals(robotName)) {
                    robot.bufferedWriter.write(commandsToServe);
                    robot.bufferedWriter.newLine();
                    robot.bufferedWriter.flush();
                }
            }
            catch(IOException e){
                closeProgram(socket, bufferedReader,  bufferedWriter);
            }
        }
    }

    public void removeRobot() {
        clientRobots.remove(this);
        displayOutput("Server: "+ robotName.toUpperCase() + " has left the game.");
    }

    public void closeProgram(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeRobot();
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        }
        catch(IOException e){
            System.out.println("");
        }
    }
}