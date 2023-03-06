package my.movies.movie.model;

import my.movies.genre.model.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Movie {
    private Integer id;
    private String title;
    private String description;
    private int year;
    private Genre genre;

    public Movie(Integer id, String title, String description, int year, Genre genre) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.year = year;
        this.genre = genre;
    }

    public Movie(ResultSet rs) throws SQLException {
        setId(rs.getInt("id"));
        setTitle(rs.getString("title"));
        setDescription(rs.getString("description"));
        setYear(rs.getInt("year"));
        setGenre(new Genre(
                rs.getInt("gen_id"),
                rs.getString("gen_name")
        ));
    }

    public Movie() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
