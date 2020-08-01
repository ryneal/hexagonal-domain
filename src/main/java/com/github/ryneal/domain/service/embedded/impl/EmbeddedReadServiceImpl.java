package com.github.ryneal.domain.service.embedded.impl;

import com.github.ryneal.domain.model.Embeddable;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.service.ReadService;
import com.github.ryneal.domain.service.embedded.EmbeddedReadService;

import java.util.Objects;
import java.util.Optional;

public final class EmbeddedReadServiceImpl<T extends Embeddable<I, U, J>, I, U extends Identifiable<J>, J>
        implements EmbeddedReadService<T, I, U, J> {

    private final ReadService<T, I> readRespository;

    public EmbeddedReadServiceImpl(ReadService<T, I> readRespository) {
        this.readRespository = readRespository;
    }

    @Override
    public Optional<T> read(J parentId, I id) {
        return this.readRespository.read(id)
                .filter(t -> Objects.nonNull(t.getParent()))
                .filter(t -> t.getParent().getId() == parentId);
    }

}
