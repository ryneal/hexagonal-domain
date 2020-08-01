package com.github.ryneal.domain.service;

import com.github.ryneal.domain.model.Identifiable;

import java.util.List;

public interface ReadAllService<T extends Identifiable<I>, I> {
    List<T> readAll();
}
