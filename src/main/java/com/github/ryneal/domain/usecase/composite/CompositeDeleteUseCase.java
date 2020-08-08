package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.Categorical;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.categorical.CategorizedDeletePort;
import com.github.ryneal.domain.usecase.DeleteUseCase;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class CompositeDeleteUseCase<T extends Identifiable<I> & Categorical<U>, I, U>
        implements DeleteUseCase<T, I> {

    private final List<CategorizedDeletePort<T, I, U>> deletePorts;

    public CompositeDeleteUseCase(List<CategorizedDeletePort<T, I, U>> deletePorts) {
        this.deletePorts = Collections.unmodifiableList(deletePorts);
    }

    @Override
    public void delete(I id) {
        Optional.ofNullable(deletePorts)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(port -> port.read(id)
                        .map(Categorical::getCategory)
                        .filter(port::supportsCategory)
                        .isPresent())
                .forEach(port -> port.delete(id));
    }

}
