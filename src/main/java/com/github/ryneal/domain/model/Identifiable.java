package com.github.ryneal.domain.model;

public interface Identifiable<I> {
    I getId();
    void setId(I id);
}
