package com.stu.fiit.mtaa.be.activity;

import com.stu.fiit.mtaa.be.appuser.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Activity {

    @Id
    @SequenceGenerator(
            name = "activity_sequence",
            sequenceName = "activity_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "activity_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(
            nullable = false
    )
    private String distance;

    @Column(
            nullable = false
    )
    private Long duration;

    @Column(
            nullable = false
    )
    private String avgSpeed;

    @Column(
            nullable = false
    )
    private String calories;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String mapRoute;

    @Column(
            nullable = false,
            columnDefinition = "BOOLEAN"
    )
    private Boolean shared;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String activityType;

    @Column(
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDate date;


}