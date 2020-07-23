package com.github.ryneal.domain.repository;

import com.github.ryneal.domain.model.Identifiable;

import java.util.Optional;

public interface ReadRepository<T extends Identifiable<I>, I> {
    Optional<T> read(I id);
}
