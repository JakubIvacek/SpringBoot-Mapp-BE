package com.stu.fiit.mtaa.be.notification;


import com.stu.fiit.mtaa.be.appuser.AppUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    //ADD NEW NOTIFICATION
    @PostMapping
    public ResponseEntity<?> addNewNotification(
            @RequestBody AddNotificationRequest addNotificationRequest
    ){
        try {
            Notification notification = notificationService.addNewNotification(addNotificationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(notification);
        }catch(EntityNotFoundException e ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //GET NOTIFICATIONS FOR USER
    @GetMapping(path="{userId}")
    public ResponseEntity<List<Notification>> returnUserNotifications(@PathVariable("userId") String userId){
        return ResponseEntity.ok(notificationService.getNotifications(userId));
    }

    //Assign FCM Token to user
    @PutMapping(path="/fcm")
    public ResponseEntity<?> assignFcmToken(
            @RequestBody String token,
            Authentication auth
    ){
        AppUser user = (AppUser) auth.getPrincipal();
        try {
            notificationService.assignFcmToken(token, user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //GET ONLY REQUEST UNACCEPTED FOR USER
    @GetMapping(path="/requests/{userId}")
    public ResponseEntity<List<Notification>> returnUserRequests(@PathVariable("userId") String userId){
        return ResponseEntity.ok(notificationService.getRequests(userId));
    }

}
