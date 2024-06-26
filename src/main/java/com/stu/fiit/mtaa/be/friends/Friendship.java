package com.stu.fiit.mtaa.be.friends;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stu.fiit.mtaa.be.appuser.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "requester_id",
            nullable = false)
    private AppUser requester;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "receiver_id",
            nullable = false)
    private AppUser receiver;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private FriendshipStatus status;
}

