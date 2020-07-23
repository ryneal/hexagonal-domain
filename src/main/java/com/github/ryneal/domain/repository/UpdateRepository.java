package com.github.ryneal.domain.repository;

import com.github.ryneal.domain.model.Identifiable;

import java.util.Optional;

public interface UpdateRepository<T extends Identifiable<I>, I> extends ReadRepository<T, I> {
    Optional<T> update(I id, T t);
}
