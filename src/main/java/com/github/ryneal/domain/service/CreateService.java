package com.github.ryneal.domain.service;

import com.github.ryneal.domain.model.Identifiable;

import java.util.Optional;

public interface CreateService<T extends Identifiable<I>, I> {
    Optional<T> create(T t);
}
