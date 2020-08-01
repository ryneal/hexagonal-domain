package com.github.ryneal.domain.service.composite;

import com.github.ryneal.domain.model.Categorical;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.ReadAllRepository;
import com.github.ryneal.domain.repository.categorical.CategorizedReadAllRepository;
import com.github.ryneal.domain.service.ReadAllService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeReadAllService<T extends Identifiable<I> & Categorical<U>, I, U>
        implements ReadAllService<T, I> {

    private final List<CategorizedReadAllRepository<T, I, U>> repositories;
    private final List<U> supportedCategories;

    public CompositeReadAllService(List<CategorizedReadAllRepository<T, I, U>> repositories,
                                   List<U> supportedCategories) {
        this.repositories = repositories;
        this.supportedCategories = supportedCategories;
    }

    @Override
    public List<T> readAll() {
        return Optional.ofNullable(this.repositories)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(repository -> this.supportedCategories.stream().anyMatch(repository::isCategory))
                .map(ReadAllRepository::readAll)
                .findFirst()
                .orElseGet(Collections::emptyList);
    }

}
