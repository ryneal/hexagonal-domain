package com.github.ryneal.domain.port.categorical;

import com.github.ryneal.domain.entity.Categorical;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.ReadPort;

public interface CategorizedReadPort<T extends Identifiable<I> & Categorical<U>, I, U>
        extends ReadPort<T, I>, CategorySupport<U> {
}
