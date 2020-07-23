package com.github.ryneal.domain.service.basic;

import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.DeleteRepository;
import com.github.ryneal.domain.service.DeleteService;

public final class BasicDeleteService<T extends Identifiable<I>, I> implements DeleteService<T, I> {

    private final DeleteRepository<T, I> repository;

    public BasicDeleteService(DeleteRepository<T, I> repository) {
        this.repository = repository;
    }

    @Override
    public void delete(I id) {
        this.repository.delete(id);
    }
}
