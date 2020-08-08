package com.github.ryneal.domain.port;

import com.github.ryneal.domain.entity.Identifiable;

import java.util.Optional;

public interface UpdatePort<T extends Identifiable<I>, I> extends ReadPort<T, I> {
    Optional<T> update(I id, T t);
}
