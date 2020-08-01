package com.github.ryneal.domain.service.basic;

import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.ReadAllRepository;
import com.github.ryneal.domain.service.ReadAllService;

import java.util.List;

public final class BasicReadAllService<T extends Identifiable<I>, I> implements ReadAllService<T, I> {

    private final ReadAllRepository<T, I> repository;

    public BasicReadAllService(ReadAllRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> readAll() {
        return this.repository.readAll();
    }
}
