package com.example.chick.models;

import java.io.Serializable;
import java.util.Objects;

public class Token implements Serializable {
    private long id;
    private String value;
    private long user_id;

    public Token() {
    }

    public Token(Long id, String value, long user_id) {
        this.id = id;
        this.value = value;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return id == token.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
