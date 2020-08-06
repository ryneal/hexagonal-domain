package com.github.ryneal.domain.port.categorical;

import com.github.ryneal.domain.entity.Categorical;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.DeletePort;
import com.github.ryneal.domain.port.ReadPort;

public interface CategorizedDeletePort<T extends Identifiable<I> & Categorical<U>, I, U>
        extends DeletePort<T, I>, CategorySupport<U>, ReadPort<T, I> {
}
