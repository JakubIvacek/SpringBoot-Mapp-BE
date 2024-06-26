package com.stu.fiit.mtaa.be.activity;

import lombok.Data;


@Data
public class FriendsActivitiesResponse {
    private Long id;
    private String owner_username;
    private String distance;
    private Long duration;
    private String calories;
    private String avgSpeed;
    private String mapRoute;
    private String activityType;
}
