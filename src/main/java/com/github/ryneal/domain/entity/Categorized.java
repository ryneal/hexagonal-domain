package com.github.ryneal.domain.entity;

public interface Categorized<T> {
    boolean isCategory(T t);
}
