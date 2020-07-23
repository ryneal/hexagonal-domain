package com.github.ryneal.domain.adapter;

public interface Adapter<T, U> {
    U adapt(T t);
}
