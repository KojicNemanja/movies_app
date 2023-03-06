package my.movies.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/my_movies", "root", "");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
