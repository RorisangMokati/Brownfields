package za.co.wethinkcode.database;

import net.lemnik.eodsql.ResultColumn;

public class ObstacleDO {
    @ResultColumn(value = "id")
    public int id;
    @ResultColumn(value = "world_id")
    public int worldId;
    @ResultColumn(value = "x_coordinate")
    public int x;
    @ResultColumn(value = "y_coordinate")
    public int y;

    public ObstacleDO() {}

    public ObstacleDO(int worldId, int x, int y) {
        this.worldId = worldId;
        this.x = x;
        this.y = y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWorldId(int worldId) {
        this.worldId = worldId;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getWorldId() {
        return worldId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}