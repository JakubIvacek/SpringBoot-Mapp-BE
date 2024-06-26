package com.stu.fiit.mtaa.be.notification;

import com.stu.fiit.mtaa.be.appuser.AppUser;
import com.stu.fiit.mtaa.be.appuser.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, AppUserRepository appUserRepository){
        this.notificationRepository = notificationRepository;
        this.appUserRepository = appUserRepository;
    }

    // Add new notification
    public Notification addNewNotification(AddNotificationRequest addNotificationRequest) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(addNotificationRequest.getUserid());
        if(appUserOptional.isPresent()){
            AppUser appUser = appUserOptional.get();
            Notification notification = Notification.builder()
                    .title(addNotificationRequest.getTitle())
                    .body(addNotificationRequest.getBody())
                    .appUser(appUser)
                    .timestamp(new Date())
                    .build();
            return notificationRepository.save(notification);
        }
        else {
            throw new EntityNotFoundException("AppUser not found");
        }
    }
    //GET ALL NOTIFICATIONS FOR USER
    public List<Notification> getNotifications(String userId) {
        return notificationRepository.findNotificationsForUser(userId);
    }

    //GET ONLY UNACCEPTED REQUESTS FOR USER
    public List<Notification> getRequests(String userId) {
        return notificationRepository.findRequestsForUser(userId);
    }

    public void assignFcmToken(String token, AppUser user) {
        user.setFcmToken(token);
        appUserRepository.save(user);
    }
}
