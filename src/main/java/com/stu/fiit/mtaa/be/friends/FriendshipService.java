package com.stu.fiit.mtaa.be.friends;

import com.stu.fiit.mtaa.be.appuser.AppUser;
import com.stu.fiit.mtaa.be.appuser.AppUserService;
import com.stu.fiit.mtaa.be.exceptions.FriendshipAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {

   private final FriendshipRepository friendshipRepository;

   private final AppUserService appUserService;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, AppUserService appUserService) {
        this.friendshipRepository = friendshipRepository;
        this.appUserService = appUserService;
    }

    public void addFriend(AppUser user, String friendName) {
        AppUser friend = appUserService.getAppUser(friendName);

        if(friendshipRepository.findFriendshipByUsersId(user.getId(), friend.getId()).isPresent()){
            throw new FriendshipAlreadyExistsException("Friendship already exists");
        }

        var friendship = Friendship.builder()
                .requester(user)
                .receiver(friend)
                .status(FriendshipStatus.ACCEPTED) // TODO: change to PENDING
                .build();

        friendshipRepository.save(friendship);
    }

    // Get friend FCM Token
    public String getFriendFCMToken(String friendName) {
        return appUserService.getUserFCMToken(friendName);

    }

    public Long getFriendshipId(Long userId, Long friendId) {
        return friendshipRepository.findFriendshipByUsersId(userId, friendId).get().getId();
    }

    public List<AppUser> getFriends(AppUser user) {
        return friendshipRepository.findAllFriendsByUserId(user.getId());
    }

    // Delete friendship by id
    public void deleteFriend(Long friendshipId) {
        if(!friendshipRepository.existsById(friendshipId)){
            throw new IllegalStateException("Friendship not found");
        }
        friendshipRepository.deleteById(friendshipId);
    }

    public List<AppUser> getFriendsUnAccepted(AppUser user) {
        return friendshipRepository.findAllFriendsByUserIdUnAccepted(user.getId());
    }

    // Get all users that are not my friends and pending requests
    public List<AppUser> getAllNonFriends(AppUser user) {
        List<AppUser> allUsers = appUserService.getAllUsers();
        List<AppUser> friends = getFriends(user);
        List<AppUser> pendingRequests = getFriendsUnAccepted(user);
        allUsers.remove(appUserService.getAppUser(user.getUsername()));
        allUsers.removeAll(friends);
        allUsers.removeAll(pendingRequests);
        return allUsers;
    }

}
