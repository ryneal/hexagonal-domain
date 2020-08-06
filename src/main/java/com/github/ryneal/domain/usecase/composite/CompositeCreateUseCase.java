package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.Categorical;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.categorical.CategorizedCreatePort;
import com.github.ryneal.domain.usecase.CreateUseCase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeCreateUseCase<T extends Identifiable<I> & Categorical<U>, I, U>
        implements CreateUseCase<T, I> {

    private final List<CategorizedCreatePort<T, I, U>> createPorts;

    public CompositeCreateUseCase(List<CategorizedCreatePort<T, I, U>> createPorts) {
        this.createPorts = Collections.unmodifiableList(createPorts);
    }

    @Override
    public Optional<T> create(T t) {
        return this.createPorts
                .stream()
                .filter(port -> port.supportsCategory(t.getCategory()))
                .flatMap(port -> port.create(t).stream())
                .findFirst();
    }

}
