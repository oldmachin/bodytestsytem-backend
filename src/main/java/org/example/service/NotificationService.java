package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.Notification;
import org.example.model.NotificationRecipient;
import org.example.repository.NotificationRecipientRepository;
import org.example.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationRecipientRepository notificationRecipientRepository;

    @Autowired
    private UserService userService;

    public Notification createNotificationDraft(Notification notification) {
        notification.setStatus(Notification.NotificationStatus.DRAFT);
        return notificationRepository.save(notification);
    }

    @Transactional
    public Notification publish(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with ID: " + notificationId));

        if (notification.getStatus() == Notification.NotificationStatus.PUBLISHED) {
            throw new IllegalStateException("Notification is already published.");
        }

        notification.setStatus(Notification.NotificationStatus.PUBLISHED);
        notification.setPublishTime(LocalDateTime.now());
        Notification savedNotification = notificationRepository.save(notification);

        List<Long> recipientIds = userService.getRecipientIdByType(notification.getTargetUserType());
        List<NotificationRecipient> recipients = recipientIds.stream()
                .map(recipientId -> new NotificationRecipient(savedNotification, recipientId))
                .toList();

        notificationRecipientRepository.saveAll(recipients);
        return savedNotification;
    }
}
