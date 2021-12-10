package com.demo.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.demo.controllers.DTO.AddFriendRequest;
import com.demo.entities.Boy;
import com.demo.entities.Friend;
import com.demo.entities.User;
import com.demo.exception.CustomException;
import com.demo.repositories.BoyRepository;
import com.demo.repositories.FriendRepository;
import com.demo.repositories.UserRepository;
import com.demo.services.auth.AuthService;
import com.demo.services.friend.FriendService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FriendController {
    public HttpServletRequest req;

    @Autowired
    FriendRepository friendRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    BoyRepository boyRepo;

    @Autowired
    FriendService friendService;

    @Autowired
    AuthService authService;

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/friend/getAll")
    public ResponseEntity getAll(HttpServletRequest req) throws CustomException {

        Long userId = authService.getUserFromCookie(req);
        if(userId== null) return  new ResponseEntity("User not login", HttpStatus.BAD_REQUEST);
        Iterable<Friend> iterable = friendRepo.findByUserId(userId).orElseThrow(() -> new CustomException("You have 0 boy in your list", 404));
        List<Boy> boyList = new ArrayList<>();
        iterable.forEach(
                friend -> {
                    boyList.add(friend.getBoy());
                }
        );
        return new ResponseEntity(boyList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/friend/add/{boyId}")
    public ResponseEntity add(@PathVariable long boyId, HttpServletRequest servletReq) throws CustomException {

        Long userId = authService.getUserFromCookie(servletReq);
        if(userId== null) return  new ResponseEntity("User not login", HttpStatus.BAD_REQUEST);

        friendService.addFriend(userId,boyId);

        return new ResponseEntity("Added this boy to your list", HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/friend/delete/{id}")
    public ResponseEntity delete(@PathVariable long id) throws CustomException {
        friendService.deleteFriend(id);
        return new ResponseEntity("OK", HttpStatus.OK);
    }

}
