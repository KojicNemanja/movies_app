package my.movies.database.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler {

    private static Connection connection = null;

    public DBHandler(){}

    public Connection getConnection() throws SQLException {
        if (connection == null){
            connection = DBConnection.getConnection();
            System.out.println("My new conn: " + connection);
        }
        return connection;
    }

    public void disable_auto_commit(){
        try{ connection.setAutoCommit(false); }catch (Exception ex){}
    }

    public void commit(){
        try{ connection.commit(); }catch (Exception ex){}
    }

    public void roll_back(){
        try{ connection.rollback(); }catch (Exception ex){}
    }

    public void close(){
        try{
            connection.close();
            System.out.println("Close conn: " + connection);
            connection = null;
        }catch (Exception ex){}
    }



    public int insert_update_delete(String query){
        int result = 0;
        Statement st = null;
        try{
            st = getConnection().createStatement();
            result =  st.executeUpdate(query);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{ st.close(); }catch (Exception ex){}
        }
        return result;
    }


}
