package com.github.ryneal.domain.port;

import com.github.ryneal.domain.entity.Identifiable;

import java.util.Optional;

public interface ReadPort<T extends Identifiable<I>, I> {
    Optional<T> read(I id);
}
