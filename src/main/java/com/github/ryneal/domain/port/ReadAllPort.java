package com.github.ryneal.domain.port;

import com.github.ryneal.domain.entity.Identifiable;

import java.util.List;

public interface ReadAllPort<T extends Identifiable<I>, I> {
    List<T> readAll();
}
