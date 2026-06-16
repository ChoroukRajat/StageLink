package com.StageLink.StageLink_back.controllers;


import com.StageLink.StageLink_back.entities.Message;
import com.StageLink.StageLink_back.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/unread/{idUtilisateur}")
    public ResponseEntity<List<Message>> getUnreadMessages(@PathVariable Long idUtilisateur) {
        List<Message> unreadMessages = messageRepository.findUnreadMessagesByUtilisateurId(idUtilisateur);
        if (unreadMessages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(unreadMessages);
    }

    @PutMapping("/{messageId}/mark-as-read")
    public ResponseEntity<Message> markMessageAsRead(@PathVariable Long messageId) {
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message == null) {
            return ResponseEntity.notFound().build(); // Return 404 if the message doesn't exist
        }

        message.setRead(true); // Set the 'read' field to true
        Message updatedMessage = messageRepository.save(message);
        return ResponseEntity.ok(updatedMessage);
    }

}
