package com.demo.services.friend;

import com.demo.controllers.DTO.AddFriendRequest;
import com.demo.entities.Boy;
import com.demo.entities.Friend;
import com.demo.entities.User;
import com.demo.exception.CustomException;
import com.demo.repositories.BoyRepository;
import com.demo.repositories.FriendRepository;
import com.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    BoyRepository boyRepo;

    @Autowired
    FriendRepository friendRepo;

    public void addFriend(AddFriendRequest req) throws CustomException {
        User user= userRepo.findById(req.getUserId()).orElseThrow(()-> new CustomException("User not found", 404));
        Boy boy =boyRepo.findById(req.getBoyId()).orElseThrow(()->new CustomException("Boy not found",404));
        Friend addFriend= new Friend(user, boy);
        friendRepo.save(addFriend);
    }

    public void deleteFriend(long id) throws  CustomException{
        Friend friend = friendRepo.findById(id).orElseThrow(()-> new CustomException("This friend not found", 404));
        friendRepo.delete(friend);
    }
}
