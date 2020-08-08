package com.github.ryneal.domain.usecase.basic;

import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.DeletePort;
import com.github.ryneal.domain.usecase.DeleteUseCase;

public final class BasicDeleteUseCase<T extends Identifiable<I>, I> implements DeleteUseCase<T, I> {

    private final DeletePort<T, I> deletePort;

    public BasicDeleteUseCase(DeletePort<T, I> deletePort) {
        this.deletePort = deletePort;
    }

    @Override
    public void delete(I id) {
        this.deletePort.delete(id);
    }

}
