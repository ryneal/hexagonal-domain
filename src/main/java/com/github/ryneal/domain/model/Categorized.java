package com.github.ryneal.domain.model;

public interface Categorized<T> {
    boolean isCategory(T t);
}
