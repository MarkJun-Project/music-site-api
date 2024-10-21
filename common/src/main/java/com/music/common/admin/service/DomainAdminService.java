package com.music.common.admin.service;

import com.music.common.admin.domian.Admin;
import com.music.common.admin.domian.AdminRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.music.common.support.ErrorCode.*;
import static com.music.common.support.Preconditions.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DomainAdminService implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Admin create(String serviceId, String password) {
        validate(!adminRepository.existsByServiceId(serviceId), ADMIN_SERVICE_ID_ALREADY_EXISTS);

        val admin = Admin.create(serviceId, password);

        return adminRepository.save(admin);
    }
}
