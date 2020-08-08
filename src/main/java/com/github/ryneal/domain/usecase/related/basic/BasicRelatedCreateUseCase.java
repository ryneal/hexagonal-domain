package com.github.ryneal.domain.usecase.related.basic;

import com.github.ryneal.domain.entity.Related;
import com.github.ryneal.domain.entity.Identifiable;
import com.github.ryneal.domain.usecase.CreateUseCase;
import com.github.ryneal.domain.usecase.ReadUseCase;
import com.github.ryneal.domain.usecase.related.RelatedCreateUseCase;

import java.util.Optional;

import static com.github.ryneal.domain.util.OptionalUtil.peek;

public final class BasicRelatedCreateUseCase<T extends Related<I, U, J>, I, U extends Identifiable<J>, J>
        implements RelatedCreateUseCase<T, I, U, J> {

    private final ReadUseCase<U, J> parentReadUseCase;
    private final CreateUseCase<T, I> createUseCase;

    public BasicRelatedCreateUseCase(ReadUseCase<U, J> parentReadUseCase,
                                     CreateUseCase<T, I> createUseCase) {
        this.parentReadUseCase = parentReadUseCase;
        this.createUseCase = createUseCase;
    }

    @Override
    public Optional<T> create(J parentId, T t) {
        return this.parentReadUseCase.read(parentId)
                .flatMap(parent -> Optional.ofNullable(t)
                        .map(peek(v -> v.setParent(parent))))
                .flatMap(this.createUseCase::create);
    }

}
