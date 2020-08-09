package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.UpdatePort;
import com.github.ryneal.domain.usecase.UpdateUseCase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeUpdateUseCase<T extends Identifiable<I>, I> implements UpdateUseCase<T, I> {

    private final List<UpdatePort<T, I>> updatePorts;

    public CompositeUpdateUseCase(List<UpdatePort<T, I>> updatePorts) {
        this.updatePorts = Collections.unmodifiableList(updatePorts);
    }

    @Override
    public Optional<T> update(I id, T t) {
        return Optional.ofNullable(this.updatePorts)
                .orElseGet(Collections::emptyList)
                .stream()
                .flatMap(port -> port.read(id)
                        .flatMap(found -> port.update(id, t)).stream())
                .findFirst();
    }

}
