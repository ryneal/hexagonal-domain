package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.ReadPort;
import com.github.ryneal.domain.usecase.ReadUseCase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeReadUseCase<T extends Identifiable<I>, I> implements ReadUseCase<T, I> {

    private final List<ReadPort<T, I>> readPorts;

    public CompositeReadUseCase(List<ReadPort<T, I>> readPorts) {
        this.readPorts = Collections.unmodifiableList(readPorts);
    }

    @Override
    public Optional<T> read(I id) {
        return Optional.ofNullable(this.readPorts)
                .orElseGet(Collections::emptyList)
                .stream()
                .flatMap(port -> port.read(id).stream())
                .findFirst();
    }

}
