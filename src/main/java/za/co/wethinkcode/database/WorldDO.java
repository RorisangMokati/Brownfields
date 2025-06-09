package za.co.wethinkcode.database;

import net.lemnik.eodsql.ResultColumn;

public class WorldDO {
    @ResultColumn(value = "id")
    public int id;
    @ResultColumn(value = "name")
    public String name;
    @ResultColumn(value = "size")
    public int size;
    @ResultColumn(value = "obstacles")
    public String obstacles;

    public WorldDO() {}

    public WorldDO(String name, int size, String obstacles) {
        this.name = name;
        this.size = size;
        this.obstacles = obstacles;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getObstacles() {
        return obstacles;
    }
}