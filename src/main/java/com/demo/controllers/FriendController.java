package com.demo.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.demo.controllers.DTO.AddFriendRequest;
import com.demo.entities.Friend;
import com.demo.entities.User;
import com.demo.exception.CustomException;
import com.demo.repositories.BoyRepository;
import com.demo.repositories.FriendRepository;
import com.demo.repositories.UserRepository;
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

@RestController
public class FriendController {

    @Autowired
    FriendRepository friendRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    BoyRepository boyRepo;

    @Autowired
    FriendService friendService;

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/friend/getAll")
    public ResponseEntity getAll(HttpServletRequest req) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.print(auth);
        Cookie[] cookies = req.getCookies();
        System.out.print(cookies[0].getValue());
        if(auth!=null || cookies!=null) {
            return new ResponseEntity(auth, HttpStatus.OK);
        } else return  new ResponseEntity("Not authen", HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/friend/add")
    public ResponseEntity add(@RequestBody AddFriendRequest req) throws CustomException {
        friendService.addFriend(req);
        return new ResponseEntity( friendRepo.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/friend/delete/{id}")
    public ResponseEntity delete(@PathVariable long id) throws CustomException {
        friendService.deleteFriend(id);
        return new ResponseEntity( friendRepo.findAll(), HttpStatus.OK);
    }

}
