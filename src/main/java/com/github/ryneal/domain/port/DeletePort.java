package com.github.ryneal.domain.port;

import com.github.ryneal.domain.entity.Identifiable;

public interface DeletePort<T extends Identifiable<I>, I> {
    void delete(I id);
}
