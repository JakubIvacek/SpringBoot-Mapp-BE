package com.stu.fiit.mtaa.be.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddActivityRequest {

        private String distance;
        private Long duration;
        private String calories;
        private String avgSpeed;
        private String activityType;
        private LocalDate date;
        private String mapRoute;
}
