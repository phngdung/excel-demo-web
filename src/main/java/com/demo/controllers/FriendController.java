package com.demo.controllers;

import com.demo.config.WebSecurityConfig;
import com.demo.controllers.DTO.AddBoyRequest;
import com.demo.entities.Boy;
import com.demo.entities.Friend;
import com.demo.exception.CustomException;
import com.demo.repositories.BoyRepository;
import com.demo.repositories.FriendRepository;
import com.demo.repositories.UserRepository;
import com.demo.services.auth.AuthService;
import com.demo.services.boy.BoyService;
import com.demo.services.excel.ExcelService;
import com.demo.services.friend.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
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

    @Autowired
    BoyService boyService;

    @Autowired
    ExcelService excelService;


    @GetMapping("/friends")
    public ResponseEntity getAll() throws CustomException {
        Long userId = authService.getCurrentUser();
        Iterable<Friend> iterable = friendRepo.findByUserId(userId).orElseThrow(() -> new CustomException("You have 0 boy in your list", 404));
        List<Boy> boyList = new ArrayList<>();
        iterable.forEach(
                friend -> {
                    if (!friend.isDelete())
                        boyList.add(friend.getBoy());
                }
        );
        return new ResponseEntity(boyList, HttpStatus.OK);

    }

    @GetMapping("/friends/add/{boyId}")
    public ResponseEntity add(@PathVariable long boyId) throws CustomException {
        long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        friendService.addFriendById(userId, boyId);
        return new ResponseEntity("Added this boy to your list", HttpStatus.OK);
    }

    @PostMapping("/friends/add")
    public ResponseEntity add(@RequestBody AddBoyRequest req) throws CustomException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Boy newBoy = boyService.add(req);
        friendService.addFriendById(userId, newBoy.getId());
        return new ResponseEntity("Added this boy to your list", HttpStatus.OK);
    }


    @GetMapping("/friends/delete/{id}")
    public ResponseEntity delete(@PathVariable long id) throws CustomException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        friendService.deleteFriend(userId, id);
        return new ResponseEntity("OK", HttpStatus.OK);
    }

    @PostMapping("/friends/export")
    public ResponseEntity export(@RequestBody List<Boy> boyList, HttpServletResponse response) throws Exception {
        excelService.export(response, boyList);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
