package za.co.wethinkcode.WebAPI;

import io.javalin.http.Context;
import za.co.wethinkcode.robotworlds.Server.Robot.Robot;
import za.co.wethinkcode.robotworlds.Server.World.Response;
import za.co.wethinkcode.robotworlds.Server.World.World;
import za.co.wethinkcode.robotworlds.Server.World.WorldConfig;

public class RobotWorldsAPI {
    // private static final QuoteDB database = new TestDatabase();
    private static WorldConfig worldConfig = new WorldConfig();
    private static World world = new World(worldConfig.extractDataFromConfigFile());

    /**
     * Get world
     *
     * @param context The Javalin Context for the HTTP GET Request
     */
    public static void getWorld(Context context) {
        context.json(world.toString());
    }

    /**
     * Get robot launch details
     *
     * @param context The Javalin Context for the HTTP GET Request
     */
    public static void getRobotLaunchResponse(Context context) {
        context.json(Response.createLaunchResponse(new Robot("null", "sniper", world)).toString());
    }

    // /**
    //  * Get one quote
    //  *
    //  * @param context The Javalin Context for the HTTP GET Request
    //  */
    // public static void getOne(Context context) {
    //     Integer id = context.pathParamAsClass("id", Integer.class).get();
    //     // Quote quote = database.get(id);
    //     // context.json(quote);
    // }

    // /**
    //  * Create a new quote
    //  *
    //  * @param context The Javalin Context for the HTTP POST Request
    //  */
    // public static void create(Context context) {
    //     Quote quote = context.bodyAsClass(Quote.class);
    //     Quote newQuote = database.add(quote);
    //     context.header("Location", "/quote/" + newQuote.getId());
    //     context.status(HttpCode.CREATED);
    //     context.json(newQuote);
    // }
}
