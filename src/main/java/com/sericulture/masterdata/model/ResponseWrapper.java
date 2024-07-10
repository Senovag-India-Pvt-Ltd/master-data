package com.sericulture.masterdata.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseWrapper<T> {

    T content;

    Long totalRecords;

    List<? extends Object> errorMessages = new ArrayList<>();

    public static <T> ResponseWrapper  createWrapper(T t) {
        return new ResponseWrapper<T>();
    }

}
