package com.github.ryneal.domain.entity;

public interface Related<I, T extends Identifiable<J>, J> extends Identifiable<I> {
    T getParent();
    void setParent(T t);
}
