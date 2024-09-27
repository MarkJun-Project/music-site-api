package com.music.common.support;


import static com.music.common.support.ErrorCode.ACTOR_VALIDATE;

public class Preconditions {

    public static void validate(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new CustomException(errorCode);
        }
    }

    public static void actorValidate(boolean expression) {
        if (!expression) {
            throw new CustomException(ACTOR_VALIDATE);
        }
    }

    public static void require(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void require(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void check(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    public static void check(boolean expression, String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

}
