package com.github.ryneal.domain.service.embedded.impl;

import com.github.ryneal.domain.model.Embeddable;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.service.ReadAllService;
import com.github.ryneal.domain.service.embedded.EmbeddedReadAllService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class EmbeddedReadAllServiceImpl<T extends Embeddable<I, U, J>, I, U extends Identifiable<J>, J>
        implements EmbeddedReadAllService<T, I, U, J> {

    private final ReadAllService<T, I> readAllRespository;

    public EmbeddedReadAllServiceImpl(ReadAllService<T, I> readAllRespository) {
        this.readAllRespository = readAllRespository;
    }

    @Override
    public List<T> readAll(J parentId) {
        return this.readAllRespository.readAll()
                .stream()
                .filter(t -> Objects.nonNull(t.getParent()))
                .filter(t -> t.getParent().getId() == parentId)
                .collect(Collectors.toList());
    }

}
