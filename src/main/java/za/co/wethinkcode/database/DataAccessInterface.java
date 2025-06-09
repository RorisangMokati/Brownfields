// DataAccessInterface.java
package za.co.wethinkcode.database;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;

import java.util.List;

public interface DataAccessInterface extends BaseQuery {
    @Select("SELECT * FROM Worlds")
    List<WorldDO> getWorlds();

    @Select("SELECT * FROM Worlds WHERE name = ?{1}")
    WorldDO getWorld(String name);

    @Select("SELECT * FROM Worlds")
    List<WorldDO> getAllWorlds();

    @Update("INSERT INTO Worlds (name, size, obstacles) VALUES (?{1.name}, ?{1.size}, ?{1.obstacles})")
    int saveWorld(WorldDO world);

    @Select("SELECT * FROM Obstacles WHERE world_id = ?{1}")
    List<ObstacleDO> getObstacles(int worldId);

    @Update("INSERT INTO Obstacles (world_id, x, y) VALUES (?{1.worldId}, ?{1.x}, ?{1.y})")
    int saveObstacle(ObstacleDO obstacle);
}