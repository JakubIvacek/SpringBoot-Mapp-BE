package com.stu.fiit.mtaa.be.messages;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.recipient.id = :recipientId AND m.sender.id = :senderId" +
            " UNION " +
            "SELECT m FROM Message m WHERE m.recipient.id = :senderId AND m.sender.id = :recipientId")
    List<Message> findMessagesForUsers(
            @Param("senderId")Long senderId, @Param("recipientId") Long recipientId);

    @Query("SELECT m.id FROM Friendship m WHERE m.receiver.id = :recipientId AND m.requester.id = :senderId" +
            " UNION " +
            "SELECT m.id FROM Friendship m WHERE m.receiver.id = :senderId AND m.requester.id = :recipientId")
    Long findFriendsID(
            @Param("senderId")Long senderId, @Param("recipientId") Long recipientId);
}
