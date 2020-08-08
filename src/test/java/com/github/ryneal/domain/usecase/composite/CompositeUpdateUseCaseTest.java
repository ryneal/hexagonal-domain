package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.entity.TestCategory;
import com.github.ryneal.domain.port.categorical.CategorizedUpdatePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompositeUpdateUseCaseTest {

    @Mock
    private CategorizedUpdatePort<BasicTestEntity, Integer, TestCategory> firstPort;
    @Mock
    private CategorizedUpdatePort<BasicTestEntity, Integer, TestCategory> secondPort;
    @Mock
    private TestCategory supportedCategory;
    private CompositeUpdateUseCase<BasicTestEntity, Integer, TestCategory> compositeUpdateUseCase;

    @BeforeEach
    void setUp() {
        this.compositeUpdateUseCase = new CompositeUpdateUseCase(Arrays.asList(this.firstPort, this.secondPort), Collections.singletonList(this.supportedCategory));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.firstPort, this.secondPort, this.supportedCategory);
    }

    @Test
    void shouldReturnEmptyWhenBothPortsDoNotSupportCategory() {
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        Integer id = 1;
        when(this.firstPort.supportsCategory(this.supportedCategory)).thenReturn(false);
        when(this.secondPort.supportsCategory(this.supportedCategory)).thenReturn(false);

        Optional<BasicTestEntity> actual = this.compositeUpdateUseCase.update(id, updateEntity);

        assertThat(actual).isEmpty();
        verify(this.firstPort).supportsCategory(this.supportedCategory);
        verify(this.secondPort).supportsCategory(this.supportedCategory);
        verifyNoMoreInteractions(updateEntity);
    }

    @Test
    void shouldReturnEmptyWhenBothPortsDoNotFindEntity() {
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        Integer id = 1;
        when(this.firstPort.supportsCategory(this.supportedCategory)).thenReturn(true);
        when(this.secondPort.supportsCategory(this.supportedCategory)).thenReturn(true);
        when(this.firstPort.read(id)).thenReturn(Optional.empty());
        when(this.secondPort.read(id)).thenReturn(Optional.empty());

        Optional<BasicTestEntity> actual = this.compositeUpdateUseCase.update(id, updateEntity);

        assertThat(actual).isEmpty();
        verify(this.firstPort).supportsCategory(this.supportedCategory);
        verify(this.secondPort).supportsCategory(this.supportedCategory);
        verify(this.firstPort).read(id);
        verify(this.secondPort).read(id);
        verifyNoMoreInteractions(updateEntity);
    }

    @Test
    void shouldReturnEmptyWhenBothPortsDoNotUpdateEntity() {
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        BasicTestEntity foundEntity = mock(BasicTestEntity.class);
        Integer id = 1;
        when(this.firstPort.supportsCategory(this.supportedCategory)).thenReturn(true);
        when(this.secondPort.supportsCategory(this.supportedCategory)).thenReturn(true);
        when(this.firstPort.read(id)).thenReturn(Optional.of(foundEntity));
        when(this.secondPort.read(id)).thenReturn(Optional.of(foundEntity));
        when(this.firstPort.update(id, updateEntity)).thenReturn(Optional.empty());
        when(this.secondPort.update(id, updateEntity)).thenReturn(Optional.empty());

        Optional<BasicTestEntity> actual = this.compositeUpdateUseCase.update(id, updateEntity);

        assertThat(actual).isEmpty();
        verify(this.firstPort).supportsCategory(this.supportedCategory);
        verify(this.secondPort).supportsCategory(this.supportedCategory);
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
        when(this.firstPort.supportsCategory(this.supportedCategory)).thenReturn(true);
        when(this.secondPort.supportsCategory(this.supportedCategory)).thenReturn(true);
        when(this.firstPort.read(id)).thenReturn(Optional.of(foundEntity));
        when(this.secondPort.read(id)).thenReturn(Optional.of(foundEntity));
        when(this.firstPort.update(id, updateEntity)).thenReturn(Optional.empty());
        when(this.secondPort.update(id, updateEntity)).thenReturn(Optional.of(expected));

        Optional<BasicTestEntity> actual = this.compositeUpdateUseCase.update(id, updateEntity);

        assertThat(actual).hasValue(expected);
        verify(this.firstPort).supportsCategory(this.supportedCategory);
        verify(this.secondPort).supportsCategory(this.supportedCategory);
        verify(this.firstPort).read(id);
        verify(this.secondPort).read(id);
        verify(this.firstPort).update(id, updateEntity);
        verify(this.secondPort).update(id, updateEntity);
        verifyNoMoreInteractions(updateEntity, foundEntity);
    }

}