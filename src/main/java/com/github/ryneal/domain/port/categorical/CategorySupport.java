package com.github.ryneal.domain.port.categorical;

public interface CategorySupport<T> {
    boolean supportsCategory(T t);
}
