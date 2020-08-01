package com.github.ryneal.domain.repository;

import com.github.ryneal.domain.model.Identifiable;

import java.util.List;

public interface ReadAllRepository<T extends Identifiable<I>, I> {
    List<T> readAll();
}
