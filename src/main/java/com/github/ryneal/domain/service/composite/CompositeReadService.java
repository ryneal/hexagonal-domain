package com.github.ryneal.domain.service.composite;

import com.github.ryneal.domain.model.Categorical;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.categorical.CategorizedReadRepository;
import com.github.ryneal.domain.service.ReadService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeReadService<T extends Identifiable<I> & Categorical<U>, I, U>
        implements ReadService<T, I> {

    private final List<CategorizedReadRepository<T, I, U>> repositories;
    private final List<U> supportedCategories;

    public CompositeReadService(List<CategorizedReadRepository<T, I, U>> repositories, List<U> supportedCategories) {
        this.repositories = repositories;
        this.supportedCategories = supportedCategories;
    }

    @Override
    public Optional<T> read(I id) {
        return Optional.ofNullable(this.repositories)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(repository -> this.supportedCategories.stream().anyMatch(repository::isCategory))
                .flatMap(repository -> repository.read(id).stream())
                .findFirst();
    }
}
