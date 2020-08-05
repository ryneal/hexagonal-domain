package com.github.ryneal.domain.usecase.related.basic;

import com.github.ryneal.domain.entity.Related;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.usecase.ReadAllUseCase;
import com.github.ryneal.domain.usecase.related.RelatedReadAllUseCase;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class BasicRelatedReadAllUseCase<T extends Related<I, U, J>, I, U extends Identifiable<J>, J>
        implements RelatedReadAllUseCase<T, I, U, J> {

    private final ReadAllUseCase<T, I> readAllUseCase;

    public BasicRelatedReadAllUseCase(ReadAllUseCase<T, I> readAllUseCase) {
        this.readAllUseCase = readAllUseCase;
    }

    @Override
    public List<T> readAll(J parentId) {
        return this.readAllUseCase.readAll()
                .stream()
                .filter(t -> Objects.nonNull(t.getParent()))
                .filter(t -> t.getParent().getId() == parentId)
                .collect(Collectors.toList());
    }

}
