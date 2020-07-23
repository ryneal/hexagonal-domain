package com.github.ryneal.domain.service;

import com.github.ryneal.domain.model.Identifiable;

import java.util.Optional;

public interface ReadService<T extends Identifiable<I>, I> {
    Optional<T> read(I id);
}
