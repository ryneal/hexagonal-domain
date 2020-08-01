package com.github.ryneal.domain.service;

import com.github.ryneal.domain.model.Identifiable;

import java.util.Optional;

public interface DeleteService<T extends Identifiable<I>, I> {
    void delete(I id);
}
