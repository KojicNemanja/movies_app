package my.movies.genre.dao;

import my.movies.database.connection.DBHandler;
import my.movies.genre.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GenreDao {

    public GenreDao(){}

    public int save(Genre genre){
        int result = 0;
        DBHandler dbHandler = new DBHandler();
        String query = String.format("""
                    INSERT INTO `genres`(`name`)
                    VALUES ('%s');""", genre.getName());
        result = dbHandler.insert_update_delete(query);
        dbHandler.close();
        return result;
    }

    public ArrayList<Genre> All(){
        DBHandler dbHandler = new DBHandler();
        ArrayList<Genre> genres = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String query = "SELECT * FROM `genres`";
            ps = dbHandler.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()){
                genres.add(new Genre(rs));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                rs.close();
                ps.close();
            }catch (Exception ex){}
            dbHandler.close();
        }
        return genres;
    }

    public Genre getForName(String gen_name){
        DBHandler dbHandler = new DBHandler();
        Genre genre = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            String query = String.format("""
                    SELECT * FROM `genres` WHERE `name` = '%s'""", gen_name);
            st = dbHandler.getConnection().createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                genre = new Genre(rs);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{ rs.close(); st.close(); }catch (Exception ex){}
            dbHandler.close();
        }
        return genre;
    }
}
