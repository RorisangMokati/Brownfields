package za.co.wethinkcode.robotworlds.Server.RobotServer;

public class CommandLineArguments {
    private static int port = 5000;
    private static int size = 1;
    private static String obstacles = "0,1";

    /**
     * Processes command line arguments to set the server's port, size, and obstacles.
     *
     * @param args The command line arguments. The arguments should be in the format:
     *             -p <port> : Specifies the port number for the server. Default is 5000.
     *             -s <size> : Specifies the size of the robot world. Default is 1.
     *             -o <obstacles> : Specifies the obstacles in the robot world. Default is "0,1".
     *
     *             The port number must be between 1 and 9999.
     *             The size must be between 1 and 200.
     *             The obstacles should be a comma-separated list of coordinates.
     *
     *             If an invalid argument is provided, an error message will be printed, and the default value will be used.
     */
    public static void processArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-p":
                    if (i + 1 < args.length) {
                        try {
                            port = Integer.parseInt(args[++i]);
                            if (port < 1 || port > 9999) {
                                throw new IllegalArgumentException("Port must be between 1 and 9999.");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid port number. Using default 5000.");
                        }
                    }
                    break;
                case "-s":
                    if (i + 1 < args.length) {
                        try {
                            size = Integer.parseInt(args[++i]);
                            if (size < 1 || size > 200) {
                                throw new IllegalArgumentException("Size must be between 1 and 200.");
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid size. Using default 2.");
                        }
                    }
                    break;
                case "-o":
                    if (i + 1 < args.length) {
                        obstacles = args[++i];
                    }
                    break;
            }
        }
    }

    public static String getObstacles() {
        return obstacles;
    }

    public static  int getSize() {
        return size;
    }

    public static int getPort() {
        return port;
    }
}
