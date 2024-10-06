package com.music.common.notice.domian;

import com.music.common.admin.domian.Admin;
import com.music.common.support.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

import static com.music.common.support.Preconditions.require;
import static java.util.Objects.nonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notice extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "ADMIN_ID", nullable = false)
    private Admin admin;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "STATUS", nullable = false)
    private NoticeStatus status = NoticeStatus.CREATED;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeAttachment> attachments = new ArrayList<>();

    private Notice(Admin admin, String title, String description) {
        this.admin = admin;
        this.title = title;
        this.description = description;
    }

    public static Notice create(Admin admin, String title, String description) {
        require(nonNull(admin));
        require(Strings.isNotBlank(title));
        require(Strings.isNotBlank(description));

        return new Notice(admin, title, description);
    }

    public void addAttachment(NoticeAttachment attachment) {
        attachments.add(attachment);
    }
}
