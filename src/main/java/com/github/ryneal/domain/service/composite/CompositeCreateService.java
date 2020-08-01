package com.github.ryneal.domain.service.composite;

import com.github.ryneal.domain.model.Categorical;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.categorical.CategorizedCreateRepository;
import com.github.ryneal.domain.service.CreateService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeCreateService<T extends Identifiable<I> & Categorical<U>, I, U>
        implements CreateService<T, I> {

    private final List<CategorizedCreateRepository<T, I, U>> repositories;

    public CompositeCreateService(List<CategorizedCreateRepository<T, I, U>> repositories) {
        this.repositories = repositories;
    }

    @Override
    public Optional<T> create(T t) {
        return Optional.ofNullable(repositories)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(repository -> repository.isCategory(t.getCategory()))
                .flatMap(repository -> repository.create(t).stream())
                .findFirst();
    }
}
