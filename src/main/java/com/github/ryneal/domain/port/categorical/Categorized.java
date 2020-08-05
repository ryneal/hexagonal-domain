package com.github.ryneal.domain.port.categorical;

public interface Categorized<T> {
    boolean isCategory(T t);
}
