package com.music.common.admin.domian;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    boolean existsByServiceId(String serviceId);
}
