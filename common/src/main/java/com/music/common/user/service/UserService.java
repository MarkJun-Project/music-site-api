package com.music.common.user.service;

import com.music.common.code.SocialType;
import com.music.common.user.domain.User;

public interface UserService {
    User loadUser(String socialId, SocialType socialType, String email);
}
