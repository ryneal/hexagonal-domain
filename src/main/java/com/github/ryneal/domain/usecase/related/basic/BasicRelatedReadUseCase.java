package com.github.ryneal.domain.usecase.related.basic;

import com.github.ryneal.domain.entity.Related;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.usecase.ReadUseCase;
import com.github.ryneal.domain.usecase.related.RelatedReadUseCase;

import java.util.Objects;
import java.util.Optional;

public final class BasicRelatedReadUseCase<T extends Related<I, U, J>, I, U extends Identifiable<J>, J>
        implements RelatedReadUseCase<T, I, U, J> {

    private final ReadUseCase<T, I> readUseCase;

    public BasicRelatedReadUseCase(ReadUseCase<T, I> readUseCase) {
        this.readUseCase = readUseCase;
    }

    @Override
    public Optional<T> read(J parentId, I id) {
        return this.readUseCase.read(id)
                .filter(t -> Objects.nonNull(t.getParent()))
                .filter(t -> t.getParent().getId() == parentId);
    }

}
