package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.port.CreatePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompositeCreateUseCaseTest {

    @Mock
    private CreatePort<BasicTestEntity, Integer> firstPort;
    @Mock
    private CreatePort<BasicTestEntity, Integer> secondPort;
    private CompositeCreateUseCase<BasicTestEntity, Integer> compositeCreateUseCase;

    @BeforeEach
    void setUp() {
        this.compositeCreateUseCase = new CompositeCreateUseCase<>(Arrays.asList(this.firstPort, this.secondPort));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.firstPort, this.secondPort);
    }

    @Test
    void shouldReturnEmptyWhenAllPortsReturnEmpty() {
        BasicTestEntity entity = mock(BasicTestEntity.class);
        when(this.firstPort.create(entity)).thenReturn(Optional.empty());
        when(this.secondPort.create(entity)).thenReturn(Optional.empty());

        Optional<BasicTestEntity> actual = this.compositeCreateUseCase.create(entity);

        assertThat(actual).isEmpty();
        verify(this.firstPort).create(entity);
        verify(this.secondPort).create(entity);
    }

    @Test
    void shouldNotCallSecondServiceWhenFirstServiceReturns() {
        BasicTestEntity entity = mock(BasicTestEntity.class);
        BasicTestEntity expected = mock(BasicTestEntity.class);
        when(this.firstPort.create(entity)).thenReturn(Optional.of(expected));

        Optional<BasicTestEntity> actual = this.compositeCreateUseCase.create(entity);

        assertThat(actual).hasValue(expected);
        verify(this.firstPort).create(entity);
    }

    @Test
    void shouldCallSecondServiceWhenFirstServiceReturnsEmpty() {
        BasicTestEntity entity = mock(BasicTestEntity.class);
        BasicTestEntity expected = mock(BasicTestEntity.class);
        when(this.firstPort.create(entity)).thenReturn(Optional.empty());
        when(this.secondPort.create(entity)).thenReturn(Optional.of(expected));

        Optional<BasicTestEntity> actual = this.compositeCreateUseCase.create(entity);

        assertThat(actual).hasValue(expected);
        verify(this.firstPort).create(entity);
        verify(this.secondPort).create(entity);
    }

}