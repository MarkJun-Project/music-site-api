package com.music.admin.controller;

import com.music.admin.security.user.AuthenticationUserException;
import com.music.admin.utils.ApiResult;
import com.music.common.support.CustomException;
import com.music.common.support.UnhandledExceptionEvent;
import com.music.integration.support.RuntimeIOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.BindException;
import java.util.NoSuchElementException;

import static com.music.common.support.ErrorCode.ACTOR_VALIDATE;
import static com.music.common.support.ErrorCode.SYSTEM_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiAdvice {

    private final ApplicationEventPublisher eventPublisher;

    @ExceptionHandler(Exception.class)
    public ApiResult<?> exception(Exception ex) {
        log.info(ex.getMessage(), ex);

        eventPublisher.publishEvent(new UnhandledExceptionEvent(ex));

        return ApiResult.error(SYSTEM_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ApiResult<?> exception(CustomException ex) {
        log.info(ex.getMessage(), ex);

        return ApiResult.error(ex.getErrorCode());
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(AuthenticationUserException.class)
    public ApiResult<?> exception(AuthenticationUserException ex) {
        log.info(ex.getMessage(), ex);

        return ApiResult.error(ACTOR_VALIDATE, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ApiResult<?> illegalArgumentException(IllegalArgumentException ex) {
        log.info(ex.getMessage(), ex);

        return ApiResult.error(SYSTEM_ERROR);
    }

    @ExceptionHandler({IllegalStateException.class})
    public ApiResult<?> illegalStateException(IllegalStateException ex) {
        log.info(ex.getMessage(), ex);

        return ApiResult.error(SYSTEM_ERROR);
    }

    @ExceptionHandler({
            BindException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
    })
    public ApiResult<?> bindException(Throwable ex) {
        log.info(ex.getMessage(), ex);

        return ApiResult.error(SYSTEM_ERROR);
    }

    @ExceptionHandler({
            HttpMediaTypeNotAcceptableException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpClientErrorException.class
    })
    public ApiResult<?> httpMediaTypeNotAcceptableException(Throwable ex) {
        log.info(ex.getMessage(), ex);

        return ApiResult.error(SYSTEM_ERROR);
    }

    @ExceptionHandler({DuplicateKeyException.class})
    public ApiResult<?> duplicateKeyException(DuplicateKeyException ex) {
        log.info(ex.getMessage(), ex);

        return ApiResult.error(SYSTEM_ERROR);
    }

    @ExceptionHandler({
            NoSuchElementException.class,
            EmptyResultDataAccessException.class
    })
    public ApiResult<?> selectException(Throwable ex) {
        log.info(ex.getMessage(), ex);

        return ApiResult.error(SYSTEM_ERROR);
    }

    @ExceptionHandler({RuntimeIOException.class})
    public ApiResult<?> uploadException(Throwable ex) {
        log.info(ex.getMessage(), ex);

        return ApiResult.error(SYSTEM_ERROR);
    }
}
