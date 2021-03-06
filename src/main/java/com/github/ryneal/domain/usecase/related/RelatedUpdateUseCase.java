package com.github.ryneal.domain.usecase.related;

import com.github.ryneal.domain.entity.Related;
import com.github.ryneal.domain.entity.Identifiable;

import java.util.Optional;

public interface RelatedUpdateUseCase<T extends Related<I, U, J>, I, U extends Identifiable<J>, J> {
    Optional<T> update(J parentId, I id, T t);
}
