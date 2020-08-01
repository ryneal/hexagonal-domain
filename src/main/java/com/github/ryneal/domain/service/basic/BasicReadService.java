package com.github.ryneal.domain.service.basic;

import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.ReadRepository;
import com.github.ryneal.domain.service.ReadService;

import java.util.Optional;

public final class BasicReadService<T extends Identifiable<I>, I> implements ReadService<T, I> {

    private final ReadRepository<T, I> repository;

    public BasicReadService(ReadRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> read(I id) {
        return this.repository.read(id);
    }

}
