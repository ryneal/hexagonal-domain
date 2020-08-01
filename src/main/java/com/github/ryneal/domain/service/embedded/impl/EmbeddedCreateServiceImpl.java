package com.github.ryneal.domain.service.embedded.impl;

import com.github.ryneal.domain.model.Embeddable;
import com.github.ryneal.domain.model.Identifiable;
import com.github.ryneal.domain.service.CreateService;
import com.github.ryneal.domain.service.ReadService;
import com.github.ryneal.domain.service.embedded.EmbeddedCreateService;

import java.util.Optional;

import static com.github.ryneal.domain.util.OptionalUtil.peek;

public final class EmbeddedCreateServiceImpl<T extends Embeddable<I, U, J>, I, U extends Identifiable<J>, J>
        implements EmbeddedCreateService<T, I, U, J> {

    private final ReadService<U, J> parentReadRespository;
    private final CreateService<T, I> createService;

    public EmbeddedCreateServiceImpl(ReadService<U, J> parentReadRespository,
                                     CreateService<T, I> createService) {
        this.parentReadRespository = parentReadRespository;
        this.createService = createService;
    }

    @Override
    public Optional<T> create(J parentId, T t) {
        return this.parentReadRespository.read(parentId)
                .flatMap(parent -> Optional.ofNullable(t)
                        .map(peek(v -> v.setParent(parent))))
                .flatMap(this.createService::create);
    }

}
