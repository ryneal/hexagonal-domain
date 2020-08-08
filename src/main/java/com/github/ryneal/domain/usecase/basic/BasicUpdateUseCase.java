package com.github.ryneal.domain.usecase.basic;

import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.UpdatePort;
import com.github.ryneal.domain.usecase.UpdateUseCase;

import java.util.Optional;

public final class BasicUpdateUseCase<T extends Identifiable<I>, I> implements UpdateUseCase<T, I> {

    private final UpdatePort<T, I> updatePort;

    public BasicUpdateUseCase(UpdatePort<T, I> updatePort) {
        this.updatePort = updatePort;
    }

    @Override
    public Optional<T> update(I id, T t) {
        return this.updatePort.read(id)
                .flatMap(found -> this.updatePort.update(id, t));
    }

}
