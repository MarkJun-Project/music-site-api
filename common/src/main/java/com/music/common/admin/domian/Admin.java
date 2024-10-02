package com.music.common.admin.domian;

import com.music.common.support.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import static com.music.common.support.Preconditions.require;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Admin extends BaseEntity {
    @Column(name = "SERVICE_ID", nullable = false)
    private String serviceId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AdminStatus status = AdminStatus.CREATED;

    private Admin(String serviceId, String password) {
        this.serviceId = serviceId;
        this.password = password;
    }

    public static Admin create(String serviceId, String password) {
        require(Strings.isNotBlank(serviceId));
        require(Strings.isNotBlank(password));

        return new Admin(serviceId, password);
    }
}
