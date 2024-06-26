package com.stu.fiit.mtaa.be.messages;

import com.stu.fiit.mtaa.be.appuser.AppUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="messages")
public class MessageController {

    private final MessageService messageService;
    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    //GET messages between sender recipient
    @GetMapping(path="/{friendId}")
    public ResponseEntity<List<MessagesResponse>> getMessages(
            @PathVariable("friendId") Long friendId,
            Authentication auth
    ){
        AppUser user = (AppUser) auth.getPrincipal();
        List<MessagesResponse> responses  = messageService.getMessages(user.getId(), friendId).stream()
                .map(message -> {
                    MessagesResponse response = new MessagesResponse();
                    response.setContent(message.getContent());
                    response.setSender_id(message.getSender().getId());
                    response.setRecipient_id(message.getRecipient().getId());
                    return response;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(responses);

    }

    //POST add new message
    @PostMapping("/{friendId}")
    public ResponseEntity<?> addNewMessage(
            @PathVariable("friendId") Long friendId,
            Authentication auth,
            @RequestBody AddMessageRequest addMessageRequest
    ){
        AppUser user = (AppUser) auth.getPrincipal();
        Message message = messageService.addNewMessage(user,friendId,addMessageRequest);

        try{
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
