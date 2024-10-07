package com.music.common.notifications.domain;

import com.music.common.support.BaseEntity;
import com.music.common.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Notifications extends BaseEntity {
    @JoinColumn(name = "RECIPIENT_ID", nullable = false)
    @ManyToOne
    private User recipient;

    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @Column(name = "STATUS", nullable = false)
    private NotificationsStatus status = NotificationsStatus.CREATED;

    protected Notifications(User recipient, String message) {
        this.recipient = recipient;
        this.message = message;
    }
}
