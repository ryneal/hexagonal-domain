package com.github.ryneal.domain.entity;

public interface Identifiable<I> {
    I getId();
    void setId(I id);
}
