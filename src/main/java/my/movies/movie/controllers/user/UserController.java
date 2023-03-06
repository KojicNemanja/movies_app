package my.movies.movie.controllers.user;

import my.movies.freemarker.template.Rendering;
import my.movies.movie.dao.MovieDao;
import my.movies.movie.model.Movie;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/")
public class UserController {

    @GetMapping()
    public String user_home(){
        ArrayList<Movie> movies = new MovieDao().all();
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("movies", movies);
        return Rendering.render("/user/home", model_data);
    }

    @GetMapping("/details/{movies_id}")
    public String movie_details(@PathVariable(name = "movies_id") int id){
        Movie movie = new MovieDao().getById(id);
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("movie", movie);
        return Rendering.render("/user/details", model_data);
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "title") String title){
        ArrayList<Movie> movies = new MovieDao().getByTitle(title);
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("movies", movies);
        return Rendering.render("/user/home", model_data);
    }
}
