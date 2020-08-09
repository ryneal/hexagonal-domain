package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.CreatePort;
import com.github.ryneal.domain.usecase.CreateUseCase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeCreateUseCase<T extends Identifiable<I>, I>
        implements CreateUseCase<T, I> {

    private final List<CreatePort<T, I>> createPorts;

    public CompositeCreateUseCase(List<CreatePort<T, I>> createPorts) {
        this.createPorts = Collections.unmodifiableList(createPorts);
    }

    @Override
    public Optional<T> create(T t) {
        return this.createPorts
                .stream()
                .flatMap(port -> port.create(t).stream())
                .findFirst();
    }

}
