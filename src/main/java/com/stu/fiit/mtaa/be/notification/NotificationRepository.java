package com.stu.fiit.mtaa.be.notification;

import com.stu.fiit.mtaa.be.messages.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    //GET ALL NOTIFICATIONS FOR USER
    @Query("SELECT n FROM Notification n WHERE n.appUser.id = :userId")
    List<Notification> findNotificationsForUser(@Param("userId") String userId);

    // GET ONLY UNACCEPTED REQUESTS FOR USER
    @Query("SELECT n FROM Notification n WHERE n.appUser.id = :userId AND n.type = 'REQUEST'")
    List<Notification> findRequestsForUser(@Param("userId") String userId);
}
