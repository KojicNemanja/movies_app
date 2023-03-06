package my.movies.staticFiles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class CssFile {

    @GetMapping("/css/**")
    @ResponseBody
    public String read() throws IOException {
        return Files.readString(Path.of(System.getenv("JAVA_RESOURCES") + "/movies_app/Static/Css/style.css"));
    }
}
