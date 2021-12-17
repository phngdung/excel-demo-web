package com.demo.controllers;

import com.demo.config.WebSecurityConfig;
import com.demo.entities.Boy;
import com.demo.entities.Friend;
import com.demo.exception.CustomException;
import com.demo.repositories.BoyRepository;
import com.demo.repositories.FriendRepository;
import com.demo.repositories.UserRepository;
import com.demo.services.auth.AuthService;
import com.demo.services.friend.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    
    @GetMapping("/friends")
    public ResponseEntity getAll() throws CustomException {
        Long userId = authService.getCurrentUser();
        Iterable<Friend> iterable = friendRepo.findByUserId(userId).orElseThrow(() -> new CustomException("You have 0 boy in your list", 404));
        List<Boy> boyList = new ArrayList<>();
        iterable.forEach(
                friend -> {
                    boyList.add(friend.getBoy());
                }
        );
        return new ResponseEntity(boyList, HttpStatus.OK);

    }

    @GetMapping("/friends/add/{boyId}")
    public ResponseEntity add(@PathVariable long boyId, HttpServletRequest servletReq) throws CustomException {
        Long userId = authService.getCurrentUser();
        friendService.addFriend(userId, boyId);
        return new ResponseEntity("Added this boy to your list", HttpStatus.OK);
    }


    @GetMapping("/friend/delete/{id}")
    public ResponseEntity delete(@PathVariable long id) throws CustomException {
        friendService.deleteFriend(id);
        return new ResponseEntity("OK", HttpStatus.OK);
    }

}
