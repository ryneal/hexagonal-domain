package com.github.ryneal.domain.port.categorical;

import com.github.ryneal.domain.entity.Categorical;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.CreatePort;

public interface CategorizedCreatePort<T extends Identifiable<I> & Categorical<U>, I, U>
        extends CreatePort<T, I>, CategorySupport<U> {
}
