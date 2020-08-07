package com.github.ryneal.domain.usecase.related.basic;

import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.entity.Related;
import com.github.ryneal.domain.usecase.ReadUseCase;
import com.github.ryneal.domain.usecase.UpdateUseCase;
import com.github.ryneal.domain.usecase.related.RelatedUpdateUseCase;

import java.util.Objects;
import java.util.Optional;

public final class BasicRelatedUpdateUseCase<T extends Related<I, U, J>, I, U extends Identifiable<J>, J>
        implements RelatedUpdateUseCase<T, I, U, J> {

    private final ReadUseCase<T, I> readUseCase;
    private final UpdateUseCase<T, I> updateUseCase;

    public BasicRelatedUpdateUseCase(ReadUseCase<T, I> readUseCase,
                                     UpdateUseCase<T, I> updateUseCase) {
        this.readUseCase = readUseCase;
        this.updateUseCase = updateUseCase;
    }

    @Override
    public Optional<T> update(J parentId, I id, T t) {
        return this.readUseCase.read(id)
                .map(Related::getParent)
                .filter(parent -> parent.getId() == parentId)
                .flatMap(child -> this.updateUseCase.update(id, t));
    }

}
