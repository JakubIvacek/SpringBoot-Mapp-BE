package com.stu.fiit.mtaa.be.messages;

import lombok.Data;

@Data
public class MessagesResponse {
    private String content;
    private Long sender_id;
    private Long recipient_id;
}
