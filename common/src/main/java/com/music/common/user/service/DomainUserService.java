package com.music.common.user.service;

import com.music.common.code.SocialType;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DomainUserService implements UserService{

    private final UserRepository userRepository;

    @Override
    public User loadUser(String socialId, SocialType socialType, String email) {
        Optional<User> userOptional = userRepository.findBySocialId(socialId);

        return userOptional
                .orElseGet(() -> userRepository.save(User.create(socialId, socialType, email)));
    }
}
