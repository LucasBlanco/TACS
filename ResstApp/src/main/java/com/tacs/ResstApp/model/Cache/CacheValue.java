package com.tacs.ResstApp.model.Cache;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CacheValue<T> {

    public CacheValue(T value) {
        this.setValue(value)
                .setTimestamp(new Date());
    }

    @JsonCreator
    public CacheValue(@JsonProperty(value = "value", required = true) T value,
                      @JsonProperty(value = "timestamp", required = true) Date timestamp) {
        this.setValue(value)
                .setTimestamp(timestamp);
    }

    private Date timestamp;
    private T value;

    public Date getTimestamp() {
        return timestamp;
    }

    public CacheValue setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public T getValue() {
        return value;
    }

    public CacheValue setValue(T value) {
        this.value = value;
        return this;
    }
}