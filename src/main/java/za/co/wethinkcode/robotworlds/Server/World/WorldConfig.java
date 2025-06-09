package za.co.wethinkcode.robotworlds.Server.World;

import org.json.JSONException;
import org.json.JSONObject;
import za.co.wethinkcode.robotworlds.Server.RobotServer.CommandLineArguments;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class WorldConfig {
    private static Scanner scanner;
    //Robot Vision

    //World Borders
    private static int borderX;
    private static int borderY;

    int vision;

    public JSONObject extractDataFromConfigFile() {
        return processJSON();
    }

    private  JSONObject processJSON() {
        String jsonString = "";
        JSONObject data;
        try {
            jsonString = readConfigFile();
            jsonString = jsonString.replace("\n", "").replace("\r", "");
        } catch (IOException e) {
            System.out.println("Problem with config file.");
        }

        try {
            data = new JSONObject(jsonString);
            data.put("New World", configInfo());
            return data;
        } catch (JSONException e) {
            System.out.println("Problem with format of config file.");
        }
        return null;
    }

    private static String readConfigFile() throws IOException {
        String result;
        try {
            String file = "src/main/resources/ConfigFile.json";
            result = Files.readString(Path.of(file));
            return result;
        } catch (FileNotFoundException e) {
            System.out.println("Config file not found.");
        } catch (IOException e) {
            System.out.println("Config file unreadable.");
        }
        return "";
    }

    //FOLLOWING EDITED BY APHIWE

    public static String add(){
        String jsonString = "";
        JSONObject data;
        try {
            jsonString = readConfigFile();
            jsonString = jsonString.replace("\n", "").replace("\r", "");
        } catch (IOException e) {
            System.out.println("Problem with config file.");
        }

        try {
            data = new JSONObject(jsonString);
//            System.out.println("I have returned '' ");

        } catch (JSONException e) {
            System.out.println("Problem with format of config file.");
        }
//        System.out.println("Add info");
        return "Add infoooooo";
    }

    public  JSONObject configInfo(){
        JSONObject jsonString = new JSONObject();
        jsonString.put("X Barriers", getXBorders());
        jsonString.put("Y Barriers", getYBorders());
        jsonString.put("Visibility", getVision());
        // Changed Shields from 0 -> 7
        jsonString.put("Shields", 7);
        // Changed Amo from 0 -> 5
        jsonString.put("Amo", 5);

        return jsonString;
    }


    public void setVision(int vision){
        this.vision = vision;
    }

    public int getVision() {
        return vision;
    }

    //Following code pertains to world borders
    //y borders
    public void setBorderY(int borderY) {
        this.borderY = borderY;
    }
    public static int getYBorders() {
        return borderY;
    }


    //x borders
    public void setBorderX(int borderX) {
        this.borderX = borderX;
    }
    public static int getXBorders() {
        return borderX;
    }

    public void setUpWorld() {
        setBorderX(CommandLineArguments.getSize());
        setBorderY(CommandLineArguments.getSize());
    }
}