package com.github.ryneal.domain.usecase.basic;

import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.CreatePort;
import com.github.ryneal.domain.usecase.CreateUseCase;

import java.util.Optional;

public final class BasicCreateUseCase<T extends Identifiable<I>, I> implements CreateUseCase<T, I> {

    private final CreatePort<T, I> createPort;

    public BasicCreateUseCase(CreatePort<T, I> createPort) {
        this.createPort = createPort;
    }

    @Override
    public Optional<T> create(T t) {
        return this.createPort.create(t);
    }

}
