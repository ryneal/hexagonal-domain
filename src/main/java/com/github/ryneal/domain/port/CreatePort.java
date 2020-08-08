package com.github.ryneal.domain.port;

import com.github.ryneal.domain.entity.Identifiable;

import java.util.Optional;

public interface CreatePort<T extends Identifiable<I>, I> {
    Optional<T> create(T t);
}
