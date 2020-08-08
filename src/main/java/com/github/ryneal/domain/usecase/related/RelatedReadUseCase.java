package com.github.ryneal.domain.usecase.related;

import com.github.ryneal.domain.entity.Related;
import com.github.ryneal.domain.entity.Identifiable;

import java.util.Optional;

public interface RelatedReadUseCase<T extends Related<I, U, J>, I, U extends Identifiable<J>, J> {
    Optional<T> read(J parentId, I id);
}
