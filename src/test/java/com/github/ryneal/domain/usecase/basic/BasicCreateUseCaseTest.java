package com.github.ryneal.domain.usecase.basic;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.port.CreatePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicCreateUseCaseTest {

    @Mock
    private CreatePort<BasicTestEntity, Integer> createPort;
    @InjectMocks
    private BasicCreateUseCase<BasicTestEntity, Integer> basicCreateUseCase;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.createPort);
    }

    @Test
    void shouldCallPortAndReturnResultWhenCalled() {
        BasicTestEntity supplied = mock(BasicTestEntity.class);
        BasicTestEntity expected = mock(BasicTestEntity.class);
        when(this.createPort.create(supplied)).thenReturn(Optional.of(expected));

        Optional actual = this.basicCreateUseCase.create(supplied);

        assertThat(actual).hasValue(expected);
        verify(this.createPort).create(supplied);
    }

}