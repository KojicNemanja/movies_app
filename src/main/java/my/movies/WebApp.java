package my.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApp {
    private static int a = 0;
    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }
}
