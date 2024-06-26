package com.stu.fiit.mtaa.be.appuser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stu.fiit.mtaa.be.activity.Activity;
import com.stu.fiit.mtaa.be.friends.Friendship;
import com.stu.fiit.mtaa.be.messages.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class AppUser implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Column(
            nullable = false,
            columnDefinition = "TEXT",
            unique = true
    )
    private String username;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String password;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String age;

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String fullName;

    private String fcmToken;

    private Long avatar_id;

    @JsonIgnore
    @OneToMany(mappedBy = "requester")
    private List<Friendship> sentRequests;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    private List<Friendship> receivedRequests;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Activity> activities;

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private List<Message> messagesSent;

    @JsonIgnore
    @OneToMany(mappedBy = "recipient")
    private List<Message> messagesReceived;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
