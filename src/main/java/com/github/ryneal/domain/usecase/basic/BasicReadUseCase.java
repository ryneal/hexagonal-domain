package com.github.ryneal.domain.usecase.basic;

import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.ReadPort;
import com.github.ryneal.domain.usecase.ReadUseCase;

import java.util.Optional;

public final class BasicReadUseCase<T extends Identifiable<I>, I> implements ReadUseCase<T, I> {

    private final ReadPort<T, I> readPort;

    public BasicReadUseCase(ReadPort<T, I> readPort) {
        this.readPort = readPort;
    }

    @Override
    public Optional<T> read(I id) {
        return this.readPort.read(id);
    }

}
