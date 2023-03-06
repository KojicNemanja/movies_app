package my.movies.genre.controllers;

import jakarta.servlet.http.HttpServletResponse;
import my.movies.freemarker.template.Rendering;
import my.movies.genre.dao.GenreDao;
import my.movies.genre.model.Genre;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class AddGenreController {

    @GetMapping("/admin/add/genre")
    @ResponseBody
    public String add_genre(){
        return Rendering.render("/admin/add_genre");
    }

    @PostMapping("/admin/add/genre")
    public void add_genre_post(@ModelAttribute(name = "gen_name") String genre,
                               HttpServletResponse response) throws IOException {
        Genre new_genre = new Genre();
        new_genre.setName(genre);
        GenreDao genreDao = new GenreDao();
        if(genreDao.save(new_genre) > 0){
            response.sendRedirect("/admin?AddGenre=true");
        }else{
            response.sendRedirect("/admin?AddGenre=false");
        }
    }
}
