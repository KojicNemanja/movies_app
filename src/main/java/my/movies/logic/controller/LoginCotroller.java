package my.movies.logic.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.movies.logic.model.User;
import my.movies.freemarker.template.Rendering;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/login")
public class LoginCotroller {

    @GetMapping()
    public String login(@RequestParam(name = "Error", required = false) String error){
        if(error != null){
            HashMap<String, Object> model_data = new HashMap<>();
            model_data.put("Error", "error");
            return Rendering.render("/admin/login", model_data);
        }
        return Rendering.render("/admin/login");
    }

    @PostMapping()
    public void login_post(@RequestParam(name = "username") String username,
                           @RequestParam(name = "password") String password,
                           HttpSession session, HttpServletResponse response) throws IOException {
        if(username.equals("admin") && password.equals("admin")){
            User user = new User(username, password);
            session.setAttribute("user", user);
            response.sendRedirect("/admin");
        }else{
            response.sendRedirect("/login?Error");
        }
    }
}
