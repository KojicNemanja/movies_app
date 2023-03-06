package my.movies.movie.controllers.admin;

import jakarta.servlet.http.HttpServletResponse;
import my.movies.freemarker.template.Rendering;
import my.movies.genre.dao.GenreDao;
import my.movies.genre.model.Genre;
import my.movies.movie.dao.MovieDao;
import my.movies.movie.model.Movie;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping()
    public String home(@RequestParam Map<String, String> params){

        ArrayList<Movie> movies = new MovieDao().all();
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("movies", movies);
        if(!params.isEmpty()) {
            for (String param : params.keySet()) {
                if(param.equals("AddMovie")){
                    model_data.put(param, params.get(param));
                }
                if(param.equals("EditMovie")){
                    model_data.put(param, params.get(param));
                }
                if(param.equals("DeleteMovie")){
                    model_data.put(param, params.get(param));
                }
                if(param.equals("AddGenre")){
                    model_data.put(param, params.get(param));
                }
            }
        }
        return Rendering.render("/admin/home", model_data);
    }

    @GetMapping("/add/movie")
    public String add_movie(){
        ArrayList<Genre> genres = new GenreDao().All();
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("genres", genres);
        return Rendering.render("/admin/add_movie", model_data);
    }

    @PostMapping("/add/movie")
    public void add_movie_post(@ModelAttribute Movie movie_data,
                               @RequestParam(name = "gen_name") String gen_name,
                               @RequestParam(name = "file") MultipartFile file,
                               HttpServletResponse response) throws IOException {
        Genre genre = new GenreDao().getForName(gen_name);
        Movie movie = new Movie();
        movie.setTitle(movie_data.getTitle());
        movie.setDescription(movie_data.getDescription());
        movie.setYear(movie_data.getYear());
        movie.setGenre(genre);
        MovieDao movieDao = new MovieDao();
        int generatedId = movieDao.save(movie);
        if(generatedId > 0){
            String images_path = System.getenv("JAVA_RESOURCES") + "/movies_app/Static/Images";
            File image = new File(images_path + "/" + generatedId + ".jpg");
            Files.write(Path.of(image.toURI()), file.getBytes());
            response.sendRedirect("/admin?AddMovie=true");
        }else{
            response.sendRedirect("/admin?AddMovie=false");
        }
    }

    @GetMapping("/edit/movie/{movies_id}")
    public String edit_movie(
            @PathVariable(name = "movies_id", required = true) int id
    ){
        Movie movie = new MovieDao().getById(id);
        ArrayList<Genre> genres = new GenreDao().All();
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("movie", movie);
        model_data.put("genres", genres);
        return Rendering.render("/admin/edit_movie", model_data);
    }

    @PostMapping("/edit/movie/{movies_id}")
    public void edit_movie_post(@ModelAttribute Movie movie_data,
                               @RequestParam(name = "gen_name") String gen_name,
                               @RequestParam(name = "file") MultipartFile file,
                               @PathVariable(name = "movies_id", required = true) int id,
                               HttpServletResponse response) throws IOException {

        System.out.println("Dovde");
        Movie movie = new MovieDao().getById(id);
        Genre genre = new GenreDao().getForName(gen_name);
        movie.setTitle(movie_data.getTitle());
        movie.setDescription(movie_data.getDescription());
        movie.setYear(movie_data.getYear());
        movie.setGenre(genre);
        int result = new MovieDao().edit(movie);
        if(result > 0){
            if(!file.isEmpty()) {
                String images_path = System.getenv("JAVA_RESOURCES") + "/movies_app/Static/Images";
                File current_image = new File(images_path + "/" + movie.getId() + ".jpg");
                current_image.delete();
                Files.write(Path.of(current_image.toURI()), file.getBytes());
            }
            response.sendRedirect("/admin?EditMovie=true");
        }else{
            response.sendRedirect("/admin?EditMovie=false");
        }
    }

    @GetMapping("/delete/movie/{movies_id}")
    public void delete_movie(@PathVariable(name = "movies_id") int id,
                             HttpServletResponse response) throws IOException {
        Movie movie = new MovieDao().getById(id);
        if(movie != null){
            if(new MovieDao().delete(id) > 0){
                String images_path = System.getenv("JAVA_RESOURCES") + "/movies_app/Static/Images";
                File image = new File(images_path + "/" + movie.getId() + ".jpg");
                image.delete();
                response.sendRedirect("/admin?DeleteMovie=true");
            }else{
                response.sendRedirect("/admin?DeleteMovie=false");
            }
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "title") String title){
        ArrayList<Movie> movies = new MovieDao().getByTitle(title);
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("movies", movies);
        return Rendering.render("/admin/home", model_data);
    }
}
