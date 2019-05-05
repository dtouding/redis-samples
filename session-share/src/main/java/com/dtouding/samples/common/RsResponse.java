package com.dtouding.samples.common;

import lombok.Getter;

import java.io.Serializable;

public class RsResponse<T> implements Serializable {

    private Boolean success;

    private String msg;

    private T data;

    public Boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public RsResponse(Boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public static <T> RsResponse<T> success(String msg, T data) {
        return new RsResponse<>(true, msg, data);
    }

    public static RsResponse error(String msg) {
        return new RsResponse(false, msg, null);
    }
}
