package com.stu.fiit.mtaa.be.messages;

import com.pusher.rest.Pusher;
import com.stu.fiit.mtaa.be.appuser.AppUser;
import com.stu.fiit.mtaa.be.appuser.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AppUserRepository appUserRepository){
        this.messageRepository = messageRepository;
        this.appUserRepository = appUserRepository;
    }

    // find messages between 2 users
    public List<Message> getMessages(Long senderId, Long recipientId) {
        return  messageRepository.findMessagesForUsers(senderId, recipientId);
    }

    // add new message
    public Message addNewMessage(AppUser user,Long friendId,AddMessageRequest addMessageRequest) {
        Optional<AppUser> recipientOptional = appUserRepository.findById(friendId);
        if(recipientOptional.isPresent()){
            AppUser recipient = recipientOptional.get();

            Message message = Message.builder()
                    .sender(user)
                    .recipient(recipient)
                    .content(addMessageRequest.getContent())
                    .timestamp(LocalDateTime.now())
                    .build();
            //send pusher
            Pusher pusher = new Pusher("1791498", "406496d9fc7f5e6862ef", "709721c13fbf3319cc00");
            pusher.setCluster("eu");
            pusher.setEncrypted(true);
            long friendsID = messageRepository.findFriendsID(friendId, user.getId());
            pusher.trigger(friendsID + "", "my-event", Collections.singletonMap("message", "new message"));
            return messageRepository.save(message);
        } else {
            throw new EntityNotFoundException("Recipient not found");
        }
    }
}
