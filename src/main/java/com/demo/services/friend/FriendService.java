package com.demo.services.friend;

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

    public void addFriendById(long userId, long boyId) throws CustomException {
        friendRepo.findByUserIdAndBoyId(userId, boyId).ifPresent(
                x -> new CustomException("That boy is already exists in yor list", 404));

        User user = userRepo.findById(userId).orElseThrow(() -> new CustomException("User not found", 404));
        Boy boy = boyRepo.findById(boyId).orElseThrow(() -> new CustomException("Boy not found", 404));

        Friend addFriend = new Friend(user, boy);
        friendRepo.save(addFriend);
    }

//    TODO deleteFriend

    public void deleteFriend(long userId, long boyId) throws CustomException {
        Friend friend = friendRepo.findByUserIdAndBoyId(userId, boyId).orElseThrow(()-> new CustomException("This friend not found", 404));
//        friendRepo.deleteById(friend.getId());
        friend.setDelete(true);
        boyRepo.deleteById(friend.getBoy().getId());
        friendRepo.save(friend);
    }
}
