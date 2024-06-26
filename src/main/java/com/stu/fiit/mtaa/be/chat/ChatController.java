package com.stu.fiit.mtaa.be.chat;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;


    @MessageMapping()
    public void processMessage(
            @Payload ChatMessage chatMessage
    ){
        ChatMessage savedMsg = chatMessageService.save(chatMessage);

        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),
                "/queue/messages",
                ChatNotification.builder()
                        .chatId(savedMsg.getChatId())
                        .senderId(savedMsg.getSenderId())
                        .recipientId(savedMsg.getRecipientId())
                        .content(savedMsg.getContent())
                        .build()
        );
    }

//    @GetMapping("/messages/{recipientId}")
//    public ResponseEntity<List<ChatMessage>> getChatMessages(
//            @PathVariable String recipientId,
//            Authentication auth
//            ) {
//        AppUser user = (AppUser) auth.getPrincipal();
//        return ResponseEntity.ok(chatMessageService.getChatMessages(user.getUsername(),recipientId));
//    }
}
