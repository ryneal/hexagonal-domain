package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.ReadAllPort;
import com.github.ryneal.domain.usecase.ReadAllUseCase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CompositeReadAllUseCase<T extends Identifiable<I>, I> implements ReadAllUseCase<T, I> {

    private final List<ReadAllPort<T, I>> readAllPorts;

    public CompositeReadAllUseCase(List<ReadAllPort<T, I>> readAllPorts) {
        this.readAllPorts = Collections.unmodifiableList(readAllPorts);
    }

    @Override
    public List<T> readAll() {
        return Optional.ofNullable(this.readAllPorts)
                .stream()
                .flatMap(List::stream)
                .map(ReadAllPort::readAll)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

}
