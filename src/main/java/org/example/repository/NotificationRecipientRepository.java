package org.example.repository;

import org.example.model.NotificationRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Long> {
    Optional<NotificationRecipientRepository> findByNotificationIdAndRecipientId(Long notificationId, Long recipientId);

    List<NotificationRecipientRepository> findByRecipientIdAndIsReadFalse(Long recipientId);
}
