package com.busbooking.models;

import java.sql.Timestamp;

public class Notification {
    private int notificationId;
    private int passengerId;
    private String message;
    private String notificationType;
    private String status;
    private Timestamp sentAt;

    public Notification(int notificationId, int passengerId, String message, String notificationType, String status, Timestamp sentAt) {
        this.notificationId = notificationId;
        this.passengerId = passengerId;
        this.message = message;
        this.notificationType = notificationType;
        this.status = status;
        this.sentAt = sentAt;
    }

    // Getters and Setters
    public int getNotificationId() { return notificationId; }
    public void setNotificationId(int notificationId) { this.notificationId = notificationId; }

    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getSentAt() { return sentAt; }
    public void setSentAt(Timestamp sentAt) { this.sentAt = sentAt; }
}
