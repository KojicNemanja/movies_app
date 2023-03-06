package my.movies.staticFiles;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class ImageFile {

    @GetMapping("/images/{img_name}")
    @ResponseBody
    public byte[] read(@PathVariable(name = "img_name") String image) throws IOException {
        return Files.readAllBytes(Path.of(System.getenv("JAVA_RESOURCES") + "/movies_app/Static/Images/" + image + ".jpg"));
    }
}
