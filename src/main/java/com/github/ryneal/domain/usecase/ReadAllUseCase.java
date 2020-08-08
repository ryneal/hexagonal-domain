package com.github.ryneal.domain.usecase;

import com.github.ryneal.domain.entity.Identifiable;

import java.util.List;

public interface ReadAllUseCase<T extends Identifiable<I>, I> {
    List<T> readAll();
}
