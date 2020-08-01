package com.github.ryneal.domain.service.composite;

import com.github.ryneal.domain.model.Categorical;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.categorical.CategorizedUpdateRepository;
import com.github.ryneal.domain.service.UpdateService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeUpdateService<T extends Identifiable<I> & Categorical<U>, I, U>
        implements UpdateService<T, I> {

    private final List<CategorizedUpdateRepository<T, I, U>> repositories;
    private final List<U> supportedCategories;

    public CompositeUpdateService(List<CategorizedUpdateRepository<T, I, U>> repositories,
                                  List<U> supportedCategories) {
        this.repositories = repositories;
        this.supportedCategories = supportedCategories;
    }

    @Override
    public Optional<T> update(I id, T t) {
        return Optional.ofNullable(this.repositories)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(repository -> this.supportedCategories.stream().anyMatch(repository::isCategory))
                .flatMap(repository -> repository.read(id)
                        .flatMap(found -> repository.update(id, t)).stream())
                .findFirst();
    }
}
