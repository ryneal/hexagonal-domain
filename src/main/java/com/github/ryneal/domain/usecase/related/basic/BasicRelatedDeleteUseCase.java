package com.github.ryneal.domain.usecase.related.basic;

import com.github.ryneal.domain.entity.Related;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.usecase.DeleteUseCase;
import com.github.ryneal.domain.usecase.ReadUseCase;
import com.github.ryneal.domain.usecase.related.RelatedDeleteUseCase;

import java.util.Objects;

public final class BasicRelatedDeleteUseCase<T extends Related<I, U, J>, I, U extends Identifiable<J>, J>
        implements RelatedDeleteUseCase<T, I, U, J> {

    private final ReadUseCase<T, I> readUseCase;
    private final DeleteUseCase<T, I> deleteUseCase;

    public BasicRelatedDeleteUseCase(ReadUseCase<T, I> readUseCase,
                                     DeleteUseCase<T, I> deleteUseCase) {
        this.readUseCase = readUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    @Override
    public void delete(J parentId, I id) {
        this.readUseCase.read(id)
                .filter(t -> Objects.nonNull(t.getParent()))
                .filter(t -> t.getParent().getId() == parentId)
                .ifPresent(t -> this.deleteUseCase.delete(id));
    }

}
