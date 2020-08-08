package com.github.ryneal.domain.util;

import com.github.ryneal.domain.entity.BasicTestEntity;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.github.ryneal.domain.util.OptionalUtil.peek;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OptionalUtilTest {

    @Test
    void shouldPeekObjectWhenUsedInOptional() {
        BasicTestEntity entity = mock(BasicTestEntity.class);

        Optional.of(entity)
                .map(peek(e -> e.setId(1)));

        verify(entity).setId(1);
    }

}