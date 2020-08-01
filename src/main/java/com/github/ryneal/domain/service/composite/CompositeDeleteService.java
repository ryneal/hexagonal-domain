package com.github.ryneal.domain.service.composite;

import com.github.ryneal.domain.model.Categorical;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.categorical.CategorizedDeleteRepository;
import com.github.ryneal.domain.service.DeleteService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeDeleteService<T extends Identifiable<I> & Categorical<U>, I, U>
        implements DeleteService<T, I> {

    private final List<CategorizedDeleteRepository<T, I, U>> repositories;

    public CompositeDeleteService(List<CategorizedDeleteRepository<T, I, U>> repositories) {
        this.repositories = repositories;
    }

    @Override
    public void delete(I id) {
        Optional.ofNullable(repositories)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(repository -> repository.read(id)
                        .map(Categorical::getCategory)
                        .filter(repository::isCategory)
                        .isPresent())
                .forEach(repository -> repository.delete(id));
    }
}
