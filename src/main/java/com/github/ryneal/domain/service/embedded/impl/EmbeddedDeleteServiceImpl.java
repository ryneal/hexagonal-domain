package com.github.ryneal.domain.service.embedded.impl;

import com.github.ryneal.domain.model.Embeddable;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.service.DeleteService;
import com.github.ryneal.domain.service.ReadService;
import com.github.ryneal.domain.service.embedded.EmbeddedDeleteService;

import java.util.Objects;

public final class EmbeddedDeleteServiceImpl<T extends Embeddable<I, U, J>, I, U extends Identifiable<J>, J>
        implements EmbeddedDeleteService<T, I, U, J> {

    private final ReadService<T, I> readRespository;
    private final DeleteService<T, I> deleteService;

    public EmbeddedDeleteServiceImpl(ReadService<T, I> readRespository,
                                     DeleteService<T, I> deleteService) {
        this.readRespository = readRespository;
        this.deleteService = deleteService;
    }

    @Override
    public void delete(J parentId, I id) {
        this.readRespository.read(id)
                .filter(t -> Objects.nonNull(t.getParent()))
                .filter(t -> t.getParent().getId() == parentId)
                .ifPresent(t -> this.deleteService.delete(id));
    }
}
