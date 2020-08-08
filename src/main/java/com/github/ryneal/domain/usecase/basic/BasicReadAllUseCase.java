package com.github.ryneal.domain.usecase.basic;

import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.ReadAllPort;
import com.github.ryneal.domain.usecase.ReadAllUseCase;

import java.util.List;

public final class BasicReadAllUseCase<T extends Identifiable<I>, I> implements ReadAllUseCase<T, I> {

    private final ReadAllPort<T, I> readAllPort;

    public BasicReadAllUseCase(ReadAllPort<T, I> readAllPort) {
        this.readAllPort = readAllPort;
    }

    @Override
    public List<T> readAll() {
        return this.readAllPort.readAll();
    }

}
