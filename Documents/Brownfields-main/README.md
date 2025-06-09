# ROBOT WORLD

## Description
* A multi-player game that allows players to configure a world to play in.
* It takes in multiple players by connecting to a server with a unique port number.
* Users are able to save as well as restore a previously configured world by specifying the name of the world they wish to save or restore.

## Getting Started
* To clone:
  * git clone git@gitlab.wethinkco.de:qntshangase023/DBN15_Brownfields_2024.git

## To Run
* mvn clean verify test
* mvn compile
* java -jar target/robot_worlds-1.0-SNAPSHOT-jar-with-dependencies.jar
    * Add arguments to configure the port number, the size as well as the position of obstacles in the world as desired.
    * -p <arg> >> Port number
    * -s <arg> >> Size of the world
    * -o <arg> >> Position of obstacles
* To run client:
  * Navigate to src/java/robotworlds/client/clientapp 
  * Run ClientApp class

## Overview
* The Robot World game runs as follows:
    * Runs on the terminal.
    * Takes command-line arguments as input.
    * Has a request and response system using a Json file.
    * It allows multiple connections on a single server.
    * The server program manages anything that has to do with the world.
    * The client program manages launching and controlling the robot.
    * Is able to save and restore data from a configured world.

### Key Features
* Robot world has the following key features:
    * Supports more than one client connection concurrently.
    * Launches more than one robot in a world.
    * Has movement commands.
    * Has battle and defending commands.
    * Has the saving and restoring capabilities for previously configured world size and obstacles.
    * Communication protocol implemented in the JSON format.
    * HTTP API Layer for external communication

## Requirements
* Have version 22 of the JDK.
* Maven 3.8.7 or above
* SQLite3

## Contributors
* Bele Lindeka
* Jali Sandile
* Mahlaba Siyethemba
* Mokati Rorisang
* Ntshangase Qhubekani
