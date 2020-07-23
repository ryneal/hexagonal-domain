package com.github.ryneal.domain.service;

import com.github.ryneal.domain.model.Identifiable;

import java.util.Optional;

public interface UpdateService<T extends Identifiable<I>, I> {
    Optional<T> update(I id, T t);
}
