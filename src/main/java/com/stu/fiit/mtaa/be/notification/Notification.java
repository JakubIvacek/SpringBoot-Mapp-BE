package com.stu.fiit.mtaa.be.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stu.fiit.mtaa.be.appuser.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private NotificationType type;
    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String title;
    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String body;
    @Column(
            nullable = false,
            columnDefinition = "DATE"
    )
    private Date timestamp;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private AppUser appUser;

}
