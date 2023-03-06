package my.movies.movie.dao;

import my.movies.database.connection.DBHandler;
import my.movies.movie.model.Movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MovieDao {

    public MovieDao(){}

    private int insert_update_delete(String query, boolean close_db){
        DBHandler dbHandler = new DBHandler();
        int result = dbHandler.insert_update_delete(query);
        if(close_db){
            dbHandler.close();
        }
        return result;
    }

    private int insertWithGeneratedKeys(String query) throws SQLException {
        int generatedId = 0;
        DBHandler dbHandler = new DBHandler();
        Statement st = dbHandler.getConnection().createStatement();
        st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = st.getGeneratedKeys();
        if(rs.next()){
            generatedId = rs.getInt(1);
        }
        return generatedId;
    }

    private Movie getMovieForQuery(String query){
        Movie movie = null;
        Statement st = null;
        ResultSet rs = null;
        DBHandler dbHandler = new DBHandler();
        try{
            st = dbHandler.getConnection().createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                movie = new Movie(rs);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            dbHandler.close();
            try{rs.close(); st.close();}catch (Exception ex){}
        }
        return movie;
    }

    private ArrayList<Movie> getAllForQuery(String query){
        ArrayList<Movie> movies = new ArrayList<>();
        DBHandler dbHandler = new DBHandler();
        Statement st = null;
        ResultSet rs = null;
        try{
            st = dbHandler.getConnection().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                movies.add(new Movie(rs));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            try{rs.close(); st.close();}catch (Exception ex){}
            dbHandler.close();
        }
        return movies;
    }

    public int save(Movie movie){
        int result = 0;
        int generatedId = 0;
        DBHandler dbHandler = new DBHandler();
        Statement st = null;
        ResultSet generatedKeys = null;
        try{
            String query = String.format("""
                    INSERT INTO `movies`(`title`, `description`, `year`)
                    VALUES ('%s', '%s', %d);""", movie.getTitle(), movie.getDescription(), movie.getYear());
            dbHandler.disable_auto_commit();
            generatedId = insertWithGeneratedKeys(query);
            if(generatedId > 0){
                query = String.format("""
                        INSERT INTO `movie_genre`(`movies_id`, `genres_id`)
                        VALUES (%d, %d);""", generatedId, movie.getGenre().getId());
                result = insert_update_delete(query, false);
                if(result > 0){
                    dbHandler.commit();
                }
            }
        }catch (Exception ex){
            dbHandler.roll_back();
            ex.printStackTrace();
            return 0;
        }finally {
            dbHandler.close();
            try{ generatedKeys.close(); st.close(); }catch (Exception ex){}
        }
        if (result > 0) return generatedId;
        return result;
    }

    public int edit(Movie movie){
        String query = String.format("""
                UPDATE movies, movie_genre
                SET movies.title='%s', movies.description='%s', movies.year=%d,
                movie_genre.genres_id=%d
                WHERE (movies.id=%d) AND (movies.id = movie_genre.movies_id);""",
                movie.getTitle(), movie.getDescription(), movie.getYear(),
                movie.getGenre().getId(), movie.getId());
        return insert_update_delete(query, true);
    }

    public int delete(int id){
        String query = String.format("DELETE FROM `movies` WHERE `id`=%d", id);
        return insert_update_delete(query, true);
    }

    public ArrayList<Movie> all(){
        String query = """
                    SELECT movies.id, movies.title, movies.description, movies.year, genres.id AS gen_id, genres.name AS gen_name
                    FROM movies, genres, movie_genre
                    WHERE (movies.id = movie_genre.movies_id) AND (genres.id = movie_genre.genres_id);""";
        return getAllForQuery(query);
    }

    public ArrayList<Movie> getByTitle(String title){
        String query = String.format("""
                    SELECT movies.id, movies.title, movies.description, movies.year, genres.id AS gen_id, genres.name AS gen_name
                    FROM movies, genres, movie_genre
                    WHERE (movies.id = movie_genre.movies_id) AND (genres.id = movie_genre.genres_id) AND (movies.title LIKE '%%%s%%');""", title);
        return getAllForQuery(query);
    }

    public Movie getById(int id){
        String query = String.format("""
                    SELECT movies.id, movies.title, movies.description, movies.year, genres.id AS gen_id, genres.name AS gen_name
                    FROM movies, genres, movie_genre
                    WHERE (movies.id = movie_genre.movies_id) AND (genres.id = movie_genre.genres_id) AND(movies.id = %d);""", id);
        return getMovieForQuery(query);
    }
}
