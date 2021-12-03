package com.demo.services.auth;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.demo.config.Token;
import com.demo.entities.User;
import com.demo.exception.CustomException;
import com.demo.repositories.UserRepository;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

import com.auth0.jwt.JWT;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepo;

    public User register(String username, String password) throws CustomException {
        if (userRepo.findByUsername(username).isPresent())
            throw new CustomException("User with username " + username + " is already exists", 404);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, hashedPassword);
        userRepo.save(newUser);
        return newUser;
    }

    public String login(String username, String password) throws CustomException {
        User user = userRepo.findByUsername(username).orElseThrow(() -> new CustomException("Username is incorrect", 404));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(password, user.getPassword())) {

            Date expiration = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(expiration);
            c.add(Calendar.DATE, Token.numOfDayJWTExp);
            expiration = c.getTime();

            String token = createJWTToken(username, expiration);
            return token;

        } else throw new CustomException("Password is incorrect ", 404);
    }
    public String createJWTToken(String username, Date expiration) {
        Algorithm signatureAlgorithm = Algorithm.HMAC256(Token.SECRET);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        String token = JWT.create()
                .withJWTId(username)
                .withIssuer("JWT_KEY")
                .withExpiresAt(expiration)
                .sign(signatureAlgorithm);
        return token;
    }
}
