package com.github.ryneal.domain.repository.categorical;

import com.github.ryneal.domain.model.Categorical;
import com.github.ryneal.domain.model.Categorized;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.repository.DeleteRepository;
import com.github.ryneal.domain.repository.ReadRepository;

public interface CategorizedDeleteRepository<T extends Identifiable<I> & Categorical<U>, I, U>
        extends DeleteRepository<T, I>, Categorized<U>, ReadRepository<T, I> {
}
