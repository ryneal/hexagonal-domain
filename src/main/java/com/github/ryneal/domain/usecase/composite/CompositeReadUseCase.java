package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.Categorical;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.categorical.CategorizedReadPort;
import com.github.ryneal.domain.usecase.ReadUseCase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeReadUseCase<T extends Identifiable<I> & Categorical<U>, I, U>
        implements ReadUseCase<T, I> {

    private final List<CategorizedReadPort<T, I, U>> readPorts;
    private final List<U> supportedCategories;

    public CompositeReadUseCase(List<CategorizedReadPort<T, I, U>> readPorts, List<U> supportedCategories) {
        this.readPorts = Collections.unmodifiableList(readPorts);
        this.supportedCategories = Collections.unmodifiableList(supportedCategories);
    }

    @Override
    public Optional<T> read(I id) {
        return Optional.ofNullable(this.readPorts)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(port -> this.supportedCategories.stream().anyMatch(port::isCategory))
                .flatMap(port -> port.read(id).stream())
                .findFirst();
    }

}
