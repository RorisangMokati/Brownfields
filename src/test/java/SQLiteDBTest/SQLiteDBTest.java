package SQLiteDBTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import za.co.wethinkcode.database.SQLiteDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SQLiteDBTest {

    private Connection connection;

    @Before
    public void setUp() throws Exception {
        // Create an in-memory SQLite database
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE Worlds (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, size INTEGER NOT NULL, obstacles TEXT NOT NULL)");
        statement.execute("CREATE TABLE Obstacles (id INTEGER PRIMARY KEY AUTOINCREMENT, world_id INTEGER NOT NULL, x INTEGER NOT NULL, y INTEGER NOT NULL, FOREIGN KEY(world_id) REFERENCES Worlds(id))");
        statement.execute("INSERT INTO Worlds (name, size, obstacles) VALUES ('Earth', 100, '[]')");
    }

    @After
    public void tearDown() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testCreateNewTables() throws Exception {
        SQLiteDB.createNewTables();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Worlds'");
        assertTrue(resultSet.next());
        assertEquals("Worlds", resultSet.getString("name"));
    }

    @Test
    public void testWorldNameExists() throws Exception {
        boolean exists = SQLiteDB.worldNameExists("Earth");
        assertTrue(exists);
    }

    @Test
    public void testGetWorldNames() throws Exception {
        List<String> worldNames = SQLiteDB.getWorldNames();
        assertTrue(worldNames.contains("Earth"));
    }
}