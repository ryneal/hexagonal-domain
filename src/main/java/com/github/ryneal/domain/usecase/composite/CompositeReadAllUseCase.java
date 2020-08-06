package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.Categorical;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.ReadAllPort;
import com.github.ryneal.domain.port.categorical.CategorizedReadAllPort;
import com.github.ryneal.domain.usecase.ReadAllUseCase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeReadAllUseCase<T extends Identifiable<I> & Categorical<U>, I, U>
        implements ReadAllUseCase<T, I> {

    private final List<CategorizedReadAllPort<T, I, U>> readAllPorts;
    private final List<U> supportedCategories;

    public CompositeReadAllUseCase(List<CategorizedReadAllPort<T, I, U>> readAllPorts,
                                   List<U> supportedCategories) {
        this.readAllPorts = Collections.unmodifiableList(readAllPorts);
        this.supportedCategories = Collections.unmodifiableList(supportedCategories);
    }

    @Override
    public List<T> readAll() {
        return Optional.ofNullable(this.readAllPorts)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(port -> this.supportedCategories.stream().anyMatch(port::supportsCategory))
                .map(ReadAllPort::readAll)
                .findFirst()
                .orElseGet(Collections::emptyList);
    }

}
