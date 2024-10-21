package com.music.common.admin.service;

import com.music.common.admin.domian.Admin;

public interface AdminService {
    Admin create(String serviceId, String password);
}
