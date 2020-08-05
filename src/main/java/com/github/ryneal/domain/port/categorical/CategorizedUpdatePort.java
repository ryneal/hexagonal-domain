package com.github.ryneal.domain.port.categorical;

import com.github.ryneal.domain.entity.Categorical;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.port.UpdatePort;

public interface CategorizedUpdatePort<T extends Identifiable<I> & Categorical<U>, I, U>
        extends UpdatePort<T, I>, Categorized<U> {
}
