package com.music.api.utils;

import com.music.common.support.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResult<T> {
    String code;
    Integer status;
    T data;

    public ApiResult() {

    }

    public ApiResult(String code, HttpStatus status, T data) {
        this.code = code;
        this.status = status.value();
        this.data = data;
    }

    public ApiResult(ErrorCode errorCode, HttpStatus status, T data) {
        this.code = errorCode.getMessage();
        this.status = status.value();
        this.data = data;
    }

    private ApiResult(HttpStatus status, T data) {
        this.status = status.value();
        this.data = data;
    }

    public static <T> ApiResult<T> created() {
        return new ApiResult<>(HttpStatus.CREATED, null);
    }

    public static <T> ApiResult<T> created(T data) {
        return new ApiResult<>(HttpStatus.CREATED, data);
    }

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(HttpStatus.OK, data);
    }

    public static <T> ApiResult<T> ok() {
        return new ApiResult<>(HttpStatus.OK, null);
    }

    public static <T> ApiResult<T> error(ErrorCode errorCode) {
        return new ApiResult<>(errorCode, HttpStatus.BAD_REQUEST, null);
    }

    public static <T> ApiResult<T> error(ErrorCode errorCode, HttpStatus status) {
        return new ApiResult<>(errorCode, status, null);
    }
}