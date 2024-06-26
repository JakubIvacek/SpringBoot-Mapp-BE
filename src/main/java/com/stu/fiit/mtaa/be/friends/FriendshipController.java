package com.stu.fiit.mtaa.be.friends;

import com.stu.fiit.mtaa.be.appuser.AppUser;
import com.stu.fiit.mtaa.be.exceptions.FriendshipAlreadyExistsException;
import com.stu.fiit.mtaa.be.notification.FirebaseMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="friends")
public class FriendshipController {

    private final FriendshipService friendshipService;
    private final FirebaseMessagingService firebaseMessagingService;


    // Get all friends
    @GetMapping
    public ResponseEntity<List<UserFriendsResponse>> getFriends(Authentication auth){
        AppUser user = (AppUser) auth.getPrincipal();
        List<UserFriendsResponse> responses = friendshipService.getFriends(user).stream()
                .map(friend -> {
                    UserFriendsResponse response = new UserFriendsResponse();
                    response.setFriendshipId(friendshipService.getFriendshipId(user.getId(), friend.getId()));
                    response.setUserId(friend.getId());
                    response.setUsername(friend.getUsername());
                    response.setFullName(friend.getFullName());
                    response.setAge(friend.getAge());
                    return response;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    // Get all friends unaccepted
    @GetMapping(path="/pending")
    public ResponseEntity<List<AppUser>> getFriendsUnAccepted(Authentication auth){
        AppUser user = (AppUser) auth.getPrincipal();
        return ResponseEntity.ok(friendshipService.getFriendsUnAccepted(user));
    }

    // Add friend
    @PostMapping(path = "/add/{friendName}")
    public ResponseEntity<?> addFriend(@PathVariable("friendName") String friendName, Authentication auth){
        AppUser user = (AppUser) auth.getPrincipal();

        try {
            friendshipService.addFriend(user, friendName);
            firebaseMessagingService.sendNotification(friendshipService.getFriendFCMToken(friendName), "New Friend", user.getUsername() + " added you as a friend");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (FriendshipAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Get all users that are not my friends or pending requests
    @GetMapping(path = "/not-friends")
    public ResponseEntity<List<AppUser>> getNonFriends(Authentication auth){
        AppUser user = (AppUser) auth.getPrincipal();
        return ResponseEntity.ok(friendshipService.getAllNonFriends(user));
    }

    // Delete friendship by Id
    @DeleteMapping(path = "/{friendshipId}")
    public ResponseEntity<?> deleteFriend(@PathVariable("friendshipId") Long friendshipId){
        try {
            friendshipService.deleteFriend(friendshipId);
            return ResponseEntity.ok().build();
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
