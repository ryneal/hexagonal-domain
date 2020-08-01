package com.github.ryneal.domain.model;

public interface Embeddable<I, T extends Identifiable<J>, J> extends Identifiable<I> {
    T getParent();
    void setParent(T t);
}
