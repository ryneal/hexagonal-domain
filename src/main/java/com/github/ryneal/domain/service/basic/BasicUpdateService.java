package com.github.ryneal.domain.service.basic;

import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.UpdateRepository;
import com.github.ryneal.domain.service.UpdateService;

import java.util.Optional;

public final class BasicUpdateService<T extends Identifiable<I>, I> implements UpdateService<T, I> {

    private final UpdateRepository<T, I> repository;

    public BasicUpdateService(UpdateRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> update(I id, T t) {
        return this.repository.read(id)
                .flatMap(found -> this.repository.update(id, t));
    }

}
