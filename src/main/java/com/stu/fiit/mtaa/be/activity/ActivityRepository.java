package com.stu.fiit.mtaa.be.activity;

import com.stu.fiit.mtaa.be.appuser.AppUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findActivitiesByUserOrderByDate(AppUser user);

    @Query("SELECT a FROM Activity a WHERE a.user IN (" +
            "SELECT f.receiver FROM Friendship f WHERE f.requester.id = :userId AND f.status = 'ACCEPTED' " +
            "UNION " +
            "SELECT f.requester FROM Friendship f WHERE f.receiver.id = :userId AND f.status = 'ACCEPTED')" +
            "AND a.shared = true ORDER BY a.date ASC")
    List<Activity> findAllSharedActivitiesByFriends(@Param("userId") Long userId);

    Optional<Activity> findActivityById(Long id);

    boolean existsById(Long id);

    @Transactional
    void deleteActivityById(Long id);

}