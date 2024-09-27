package com.music.core.support;

import com.music.common.support.CustomException;
import com.music.common.support.ErrorCode;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.ThrowableTypeAssert;

import static com.music.common.support.ErrorCode.ACTOR_VALIDATE;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public final class ExceptionTest {

    private final ErrorCode errorCode;
    private final ThrowableTypeAssert<CustomException> throwableTypeAssert;

    public ExceptionTest(ErrorCode errorCode, ThrowableTypeAssert<CustomException> throwableTypeAssert) {
        this.errorCode = errorCode;
        this.throwableTypeAssert = throwableTypeAssert;
    }

    public static ExceptionTest assertThatCustomException(ErrorCode errorCode) {
        return new ExceptionTest(errorCode, assertThatExceptionOfType(CustomException.class));
    }

    public static ExceptionTest assertThatActorValidateCustomException() {
        return new ExceptionTest(ACTOR_VALIDATE, assertThatExceptionOfType(CustomException.class));
    }

    public void isThrownBy(ThrowableAssert.ThrowingCallable throwingCallable) {
        throwableTypeAssert.isThrownBy(throwingCallable).withMessage(errorCode.getMessage());
    }
}
