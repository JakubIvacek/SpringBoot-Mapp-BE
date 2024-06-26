package com.stu.fiit.mtaa.be.messages;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stu.fiit.mtaa.be.appuser.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String content;

    @Column(
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime timestamp;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "sender",
            nullable = false
    )
    private AppUser sender;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "recipient",
            nullable = false
    )
    private AppUser recipient;
}
