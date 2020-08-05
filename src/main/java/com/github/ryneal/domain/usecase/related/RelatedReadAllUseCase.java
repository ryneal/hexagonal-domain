package com.github.ryneal.domain.usecase.related;

import com.github.ryneal.domain.entity.Related;
import com.github.ryneal.domain.entity.Identifiable;

import java.util.List;

public interface RelatedReadAllUseCase<T extends Related<I, U, J>, I, U extends Identifiable<J>, J> {
    List<T> readAll(J parentId);
}
