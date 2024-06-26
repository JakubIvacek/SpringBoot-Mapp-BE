package com.stu.fiit.mtaa.be.friends;

import com.stu.fiit.mtaa.be.appuser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("SELECT f.receiver FROM Friendship f WHERE f.requester.id = :userId AND f.status = 'ACCEPTED' " +
            "UNION " +
            "SELECT f.requester FROM Friendship f WHERE f.receiver.id = :userId AND f.status = 'ACCEPTED'")
    List<AppUser> findAllFriendsByUserId(@Param("userId") Long userId);


    @Query("SELECT f.receiver FROM Friendship f WHERE f.requester.id = :userId AND f.status = 'PENDING' " +
            "UNION " +
            "SELECT f.requester FROM Friendship f WHERE f.receiver.id = :userId AND f.status = 'PENDING'")
    List<AppUser> findAllFriendsByUserIdUnAccepted(@Param("userId") Long userId);

    // Check if friendship already exists
    @Query("SELECT f FROM Friendship f WHERE f.requester.id = :requesterId AND f.receiver.id = :receiverId " +
            "OR f.requester.id = :receiverId AND f.receiver.id = :requesterId")
    Optional<Friendship> findFriendshipByUsersId(@Param("requesterId") Long requesterId, @Param("receiverId") Long receiverId);
}
