package my.movies.genre.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Genre {
    private Integer id;
    private String name;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(ResultSet rs) throws SQLException {
        setId(rs.getInt("id"));
        setName(rs.getString("name"));
    }

    public Genre() {
        this(null, "");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
