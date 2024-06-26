package com.stu.fiit.mtaa.be.activity;

import lombok.Data;


@Data
public class UserActivitiesResponse {
    private Long id;
    private String distance;
    private Long duration;
    private String avgSpeed;
    private String calories;
    private String mapRoute;
    private String activityType;
    private String date;
}
