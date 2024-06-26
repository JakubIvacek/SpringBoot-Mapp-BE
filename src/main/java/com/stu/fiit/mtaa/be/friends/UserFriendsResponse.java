package com.stu.fiit.mtaa.be.friends;

import lombok.Data;

@Data
public class UserFriendsResponse {
    private Long friendshipId;
    private Long userId;
    private String username;
    private String fullName;
    private String age;
//    private byte[] image;
}
