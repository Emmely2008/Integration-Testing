package data;

import model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataAccessPostGreSQL implements DataAccessor {
    private static final boolean DEBUG = true;
    private String name;
    private DBConnectorPostGres dbConnectorPostGres;
    public DataAccessPostGreSQL(DBConnectorPostGres dbConnectorPostGres){
        this.name = "PostGreSQL";
        this.dbConnectorPostGres = dbConnectorPostGres;
    }
    public List<Person> getAllPersonsDepthOne(String person) {
        List<Person> list = new ArrayList();

        try{
            Connection connection = this.dbConnectorPostGres.getConnection();
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM chinook.person a JOIN  (select * FROM chinook.endorsement e JOIN chinook.person p ON e.source_node_id = p.id WHERE p.name='"+person+"') b ON a.id=b.target_node_id;";
            ResultSet res = stmt.executeQuery(query);
            while(res.next()){
                Person p = new Person();
                //p.setId(res.getString("id"));
                p.setJob(res.getString("job"));
                p.setBirthday(res.getString("birthday"));
                p.setName(res.getString("name"));
                list.add(p);
            }
        }catch(Exception e){
            if(DEBUG) e.printStackTrace();
        }
        return list;
    }

    public List<Person> getAllPersonsDepthTwo(String person) {
        return null;
    }

    public List<Person> getAllPersonsDepthThree(String person) {
        return null;
    }

    public List<Person> getAllPersonsDepthFour(String person) {
        return null;
    }

    public List<Person> getAllPersonsDepthFive(String person) {
        return null;
    }

    public String getName() {
        return this.name;
    }
}
