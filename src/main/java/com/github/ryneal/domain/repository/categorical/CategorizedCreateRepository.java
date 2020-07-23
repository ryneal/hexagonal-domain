package com.github.ryneal.domain.repository.categorical;

import com.github.ryneal.domain.model.Categorical;
import com.github.ryneal.domain.model.Categorized;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.CreateRepository;

public interface CategorizedCreateRepository<T extends Identifiable<I> & Categorical<U>, I, U>
        extends CreateRepository<T, I>, Categorized<U> {
}
