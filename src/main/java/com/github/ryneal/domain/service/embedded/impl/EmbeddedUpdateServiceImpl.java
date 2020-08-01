package com.github.ryneal.domain.service.embedded.impl;

import com.github.ryneal.domain.model.Embeddable;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.service.ReadService;
import com.github.ryneal.domain.service.UpdateService;
import com.github.ryneal.domain.service.embedded.EmbeddedUpdateService;
import com.github.ryneal.domain.util.OptionalUtil;

import java.util.Objects;
import java.util.Optional;

public final class EmbeddedUpdateServiceImpl<T extends Embeddable<I, U, J>, I, U extends Identifiable<J>, J>
        implements EmbeddedUpdateService<T, I, U, J> {

    private final ReadService<T, I> readRespository;
    private final UpdateService<T, I> updateService;

    public EmbeddedUpdateServiceImpl(ReadService<T, I> readRespository,
                                     UpdateService<T, I> updateService) {
        this.readRespository = readRespository;
        this.updateService = updateService;
    }

    @Override
    public Optional<T> update(J parentId, I id, T t) {
        return this.readRespository.read(id)
                .filter(child -> Objects.nonNull(t.getParent()))
                .filter(child -> t.getParent().getId() == parentId)
                .flatMap(child -> this.updateService.update(id, child));
    }

}
