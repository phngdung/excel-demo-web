package com.demo.repositories;

import com.demo.entities.Friend;
import com.demo.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Long> {
    Optional<Iterable<Friend>> findByUserId(long userId);
    Optional<Friend> findByUserIdAndBoyId(long userId, long boyId);

}