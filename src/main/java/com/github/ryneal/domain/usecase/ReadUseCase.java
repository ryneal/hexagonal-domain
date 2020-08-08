package com.github.ryneal.domain.usecase;

import com.github.ryneal.domain.entity.Identifiable;

import java.util.Optional;

public interface ReadUseCase<T extends Identifiable<I>, I> {
    Optional<T> read(I id);
}
