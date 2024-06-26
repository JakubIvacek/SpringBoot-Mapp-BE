package com.stu.fiit.mtaa.be.notification;


import lombok.Data;

@Data
public class AddNotificationRequest {
    private Enum<NotificationType> notificationTypeEnum;
    private String title;
    private String body;
    private Long userid;
}
