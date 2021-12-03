package com.demo.controllers;

import com.demo.config.Token;
import com.demo.controllers.DTO.UserRegisterRequest;
import com.demo.entities.User;
import com.demo.exception.CustomException;
import com.demo.repositories.UserRepository;
import com.demo.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@RestController
public class AuthController {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/me")
    public ResponseEntity getAllBoys() throws Exception {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/auth/register")
    @ResponseBody
    public ResponseEntity register(@RequestBody UserRegisterRequest req) throws CustomException {
        User user = authService.register(req.getUsername(), req.getPassword());
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/auth/login")
    @ResponseBody
    public ResponseEntity login(@RequestBody UserRegisterRequest req, HttpServletResponse response) throws CustomException {
        String token = authService.login(req.getUsername(), req.getPassword());
        Integer numOfDay = 1;
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(Token.numOfDayJWTExp * 24 * 60 * 60);
        response.addCookie(cookie);
        return new ResponseEntity<>("Login successfully", HttpStatus.OK);
    }

//    @CrossOrigin(origins = "http://localhost:8080")
//    @GetMapping("/auth/logout")
//    @ResponseBody
//    public ResponseEntity logout(HttpSecurity http) throws Exception {
//        http.logout(logout -> logout
//                        .logoutUrl("/auth/logout")
//                        .addLogoutHandler((request, response, auth) -> {
//                            for (Cookie cookie : request.getCookies()) {
//                                String cookieName = cookie.getName();
//                                Cookie cookieToDelete = new Cookie(cookieName, null);
//                                cookieToDelete.setMaxAge(0);
//                                response.addCookie(cookieToDelete);
//                            }
//                        })
//                );
//        return new ResponseEntity<>("Logout", HttpStatus.OK);
//    }

}
