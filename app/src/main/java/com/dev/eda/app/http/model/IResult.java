package com.dev.eda.app.http.model;

public interface IResult<T> {

    boolean isSuccess();

    int getCode();

    T data();
}
