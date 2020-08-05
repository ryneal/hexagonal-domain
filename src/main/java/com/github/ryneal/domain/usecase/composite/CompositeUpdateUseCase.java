package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.Categorical;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.categorical.CategorizedUpdatePort;
import com.github.ryneal.domain.usecase.UpdateUseCase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeUpdateUseCase<T extends Identifiable<I> & Categorical<U>, I, U>
        implements UpdateUseCase<T, I> {

    private final List<CategorizedUpdatePort<T, I, U>> updatePorts;
    private final List<U> supportedCategories;

    public CompositeUpdateUseCase(List<CategorizedUpdatePort<T, I, U>> updatePorts,
                                  List<U> supportedCategories) {
        this.updatePorts = Collections.unmodifiableList(updatePorts);
        this.supportedCategories = Collections.unmodifiableList(supportedCategories);
    }

    @Override
    public Optional<T> update(I id, T t) {
        return Optional.ofNullable(this.updatePorts)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(port -> this.supportedCategories.stream().anyMatch(port::isCategory))
                .flatMap(port -> port.read(id)
                        .flatMap(found -> port.update(id, t)).stream())
                .findFirst();
    }

}
