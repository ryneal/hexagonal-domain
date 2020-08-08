package com.github.ryneal.domain.usecase;

import com.github.ryneal.domain.entity.Identifiable;

public interface DeleteUseCase<T extends Identifiable<I>, I> {
    void delete(I id);
}
