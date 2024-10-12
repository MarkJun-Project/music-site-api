package com.music.common.support;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    SYSTEM_ERROR("system.error"),
    ACTOR_VALIDATE("error.actor.validate"),
    INVALID_MEMBER_SIZE("error.member.size.validate"),
    NOT_EXISTED_MEMBER("error.member.not.existed"),
    ALREADY_EXISTED_MEMBER("error.member.already.existed"),
    ANOTHER_EXISTED_MEMBER("error.member.another.existed"),
    NOT_ALLOWED_AS_LEADER("error.member.not.allowed.as.leader"),
    BOARD_ALREADY_EVALUATED("error.board.already.evaluated"),
    ALREADY_LIKED("error.like.already.existed");

    private final String message;
}
