package com.github.ryneal.domain.port.categorical;

import com.github.ryneal.domain.entity.Categorical;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.ReadAllPort;

public interface CategorizedReadAllPort<T extends Identifiable<I> & Categorical<U>, I, U>
        extends ReadAllPort<T, I>, CategorySupport<U> {
}
