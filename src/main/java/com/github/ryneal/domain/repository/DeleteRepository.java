package com.github.ryneal.domain.repository;

import com.github.ryneal.domain.model.Identifiable;

public interface DeleteRepository<T extends Identifiable<I>, I> {
    void delete(I id);
}
