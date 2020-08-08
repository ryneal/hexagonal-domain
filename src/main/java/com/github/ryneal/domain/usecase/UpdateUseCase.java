package com.github.ryneal.domain.usecase;

import com.github.ryneal.domain.entity.Identifiable;

import java.util.Optional;

public interface UpdateUseCase<T extends Identifiable<I>, I> {
    Optional<T> update(I id, T t);
}
