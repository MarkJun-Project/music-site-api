package com.music.common.admin.service;

import com.music.common.admin.domian.Admin;
import com.music.common.admin.domian.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DomainAdminService implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Admin create(String serviceId, String password) {
        val admin = Admin.create(serviceId, password);

        return adminRepository.save(admin);
    }
}
