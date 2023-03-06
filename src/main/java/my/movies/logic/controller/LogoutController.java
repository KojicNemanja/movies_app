package my.movies.logic.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.movies.logic.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    @GetMapping()
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        if(user != null){
            session.removeAttribute("user");
        }
        response.sendRedirect("/login");
    }
}
