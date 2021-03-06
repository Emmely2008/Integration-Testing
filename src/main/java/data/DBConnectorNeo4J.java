package data;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

import java.sql.Connection;

public class DBConnectorNeo4J {
    private Driver driver = null;
    //Constants
    private static final String IP	     = "bolt://167.99.249.26";
    private static final String PORT     = "";
    private static final String USERNAME = "neo4j";
    private static final String PASSWORD = "class";

    public DBConnectorNeo4J(){
        this.driver = GraphDatabase.driver(
                IP,
                AuthTokens.basic( USERNAME, PASSWORD ) );
    }
    public Driver getDriver() {
        return this.driver;
    }
}
