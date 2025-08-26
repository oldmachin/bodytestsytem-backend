package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_recipients")
public class NotificationRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;

    private Long recipientId;

    private boolean isRead = false;

    private LocalDateTime readTime;

    public NotificationRecipient() {
    }

    public NotificationRecipient(Notification notification, Long recipientId) {
        this.notification = notification;
        this.recipientId = recipientId;
    }

    public Long getId() {
        return id;
    }

    public Notification getNotification() {
        return notification;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public boolean isRead() {
        return isRead;
    }

    public LocalDateTime getReadTime() {
        return readTime;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setReadTime(LocalDateTime readTime) {
        this.readTime = readTime;
    }
}