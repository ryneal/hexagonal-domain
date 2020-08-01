package com.github.ryneal.domain.service.basic;

import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.CreateRepository;
import com.github.ryneal.domain.service.CreateService;

import java.util.Optional;

public final class BasicCreateService<T extends Identifiable<I>, I> implements CreateService<T, I> {

    private final CreateRepository<T, I> repository;

    public BasicCreateService(CreateRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> create(T t) {
        return this.repository.create(t);
    }

}
