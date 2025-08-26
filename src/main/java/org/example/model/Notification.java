package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    public enum NotificationType {
        SYSTEM_MESSAGE,
        ANNOUNCEMENT,
        ALERT
    }

    public enum NotificationStatus {
        DRAFT,
        PUBLISHED,
        ARCHIVED
    }

    public enum TargetUserType {
        ALL,
        STUDENT,
        TEACHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private Long creatorId;

    private LocalDateTime publishTime;

    @Enumerated(EnumType.STRING)
    private TargetUserType targetUserType;

    public Notification() {}

    public Notification(String title, String content, NotificationType type, NotificationStatus status, Long creatorId, TargetUserType targetUserType) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.status = status;
        this.creatorId = creatorId;
        this.targetUserType = targetUserType;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public NotificationType getType() {
        return type;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public TargetUserType getTargetUserType() {
        return targetUserType;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public void setTargetUserType(TargetUserType targetUserType) {
        this.targetUserType = targetUserType;
    }
}