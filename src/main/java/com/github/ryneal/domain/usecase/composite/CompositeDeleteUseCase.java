package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.DeletePort;
import com.github.ryneal.domain.usecase.DeleteUseCase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeDeleteUseCase<T extends Identifiable<I>, I> implements DeleteUseCase<T, I> {

    private final List<DeletePort<T, I>> deletePorts;

    public CompositeDeleteUseCase(List<DeletePort<T, I>> deletePorts) {
        this.deletePorts = Collections.unmodifiableList(deletePorts);
    }

    @Override
    public void delete(I id) {
        Optional.ofNullable(deletePorts)
                .stream()
                .flatMap(List::stream)
                .forEach(port -> port.delete(id));
    }

}
