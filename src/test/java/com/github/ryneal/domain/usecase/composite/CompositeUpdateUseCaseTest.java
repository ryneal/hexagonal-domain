package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.port.UpdatePort;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompositeUpdateUseCaseTest {

    @Mock
    private UpdatePort<BasicTestEntity, Integer> firstPort;
    @Mock
    private UpdatePort<BasicTestEntity, Integer> secondPort;

    private CompositeUpdateUseCase<BasicTestEntity, Integer> compositeUpdateUseCase;

    @BeforeEach
    void setUp() {
        this.compositeUpdateUseCase = new CompositeUpdateUseCase(Arrays.asList(this.firstPort, this.secondPort));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.firstPort, this.secondPort);
    }

    @Test
    void shouldReturnEmptyWhenBothPortsDoNotFindEntity() {
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        Integer id = 1;
        when(this.firstPort.read(id)).thenReturn(Optional.empty());
        when(this.secondPort.read(id)).thenReturn(Optional.empty());

        Optional<BasicTestEntity> actual = this.compositeUpdateUseCase.update(id, updateEntity);

        assertThat(actual).isEmpty();
        verify(this.firstPort).read(id);
        verify(this.secondPort).read(id);
        verifyNoMoreInteractions(updateEntity);
    }

    @Test
    void shouldReturnEmptyWhenBothPortsDoNotUpdateEntity() {
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        BasicTestEntity foundEntity = mock(BasicTestEntity.class);
        Integer id = 1;
        when(this.firstPort.read(id)).thenReturn(Optional.of(foundEntity));
        when(this.secondPort.read(id)).thenReturn(Optional.of(foundEntity));
        when(this.firstPort.update(id, updateEntity)).thenReturn(Optional.empty());
        when(this.secondPort.update(id, updateEntity)).thenReturn(Optional.empty());

        Optional<BasicTestEntity> actual = this.compositeUpdateUseCase.update(id, updateEntity);

        assertThat(actual).isEmpty();
        verify(this.firstPort).read(id);
        verify(this.secondPort).read(id);
        verify(this.firstPort).update(id, updateEntity);
        verify(this.secondPort).update(id, updateEntity);
        verifyNoMoreInteractions(updateEntity, foundEntity);
    }

    @Test
    void shouldReturnUpdatedEntityWhenSecondPortUpdatesEntity() {
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        BasicTestEntity foundEntity = mock(BasicTestEntity.class);
        BasicTestEntity expected = mock(BasicTestEntity.class);
        Integer id = 1;
        when(this.firstPort.read(id)).thenReturn(Optional.of(foundEntity));
        when(this.secondPort.read(id)).thenReturn(Optional.of(foundEntity));
        when(this.firstPort.update(id, updateEntity)).thenReturn(Optional.empty());
        when(this.secondPort.update(id, updateEntity)).thenReturn(Optional.of(expected));

        Optional<BasicTestEntity> actual = this.compositeUpdateUseCase.update(id, updateEntity);

        assertThat(actual).hasValue(expected);
        verify(this.firstPort).read(id);
        verify(this.secondPort).read(id);
        verify(this.firstPort).update(id, updateEntity);
        verify(this.secondPort).update(id, updateEntity);
        verifyNoMoreInteractions(updateEntity, foundEntity);
    }

}