package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.entity.TestCategory;
import com.github.ryneal.domain.port.categorical.CategorizedDeletePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompositeDeleteUseCaseTest {

    @Mock
    private CategorizedDeletePort<BasicTestEntity, Integer, TestCategory> firstPort;
    @Mock
    private CategorizedDeletePort<BasicTestEntity, Integer, TestCategory> secondPort;
    private CompositeDeleteUseCase<BasicTestEntity, Integer, TestCategory> compositeDeleteUseCase;

    @BeforeEach
    void setUp() {
        this.compositeDeleteUseCase = new CompositeDeleteUseCase(Arrays.asList(this.firstPort, this.secondPort));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.firstPort, this.secondPort);
    }

    @Test
    void shouldPerformNoActionWhenThereIsAnEmptyPortList() {
        Integer id = Integer.MIN_VALUE;
        this.compositeDeleteUseCase = new CompositeDeleteUseCase<>(Collections.emptyList());

        this.compositeDeleteUseCase.delete(id);
    }

    @Test
    void shouldPerformNoActionsWhenBothEntitiesHaveNullCategories() {
        Integer id = Integer.MIN_VALUE;
        BasicTestEntity firstEntity = mock(BasicTestEntity.class);
        BasicTestEntity secondEntity = mock(BasicTestEntity.class);
        when(firstEntity.getCategory()).thenReturn(null);
        when(secondEntity.getCategory()).thenReturn(null);
        when(this.firstPort.read(id)).thenReturn(Optional.of(firstEntity));
        when(this.secondPort.read(id)).thenReturn(Optional.of(secondEntity));

        this.compositeDeleteUseCase.delete(id);

        verify(this.firstPort).read(id);
        verify(this.secondPort).read(id);
        verify(firstEntity).getCategory();
        verify(secondEntity).getCategory();
    }

    @Test
    void shouldPerformNoActionsWhenBothPortsDoNotMatchCategories() {
        Integer id = Integer.MIN_VALUE;
        BasicTestEntity firstEntity = mock(BasicTestEntity.class);
        BasicTestEntity secondEntity = mock(BasicTestEntity.class);
        TestCategory category = mock(TestCategory.class);
        when(firstEntity.getCategory()).thenReturn(category);
        when(secondEntity.getCategory()).thenReturn(category);
        when(this.firstPort.read(id)).thenReturn(Optional.of(firstEntity));
        when(this.secondPort.read(id)).thenReturn(Optional.of(secondEntity));
        when(this.firstPort.supportsCategory(category)).thenReturn(false);
        when(this.secondPort.supportsCategory(category)).thenReturn(false);

        this.compositeDeleteUseCase.delete(id);

        verify(this.firstPort).read(id);
        verify(this.secondPort).read(id);
        verify(this.firstPort).supportsCategory(category);
        verify(this.secondPort).supportsCategory(category);
        verify(firstEntity).getCategory();
        verify(secondEntity).getCategory();
    }

    @Test
    void shouldPerformActionsWhenBothPortsMatchCategories() {
        Integer id = Integer.MIN_VALUE;
        BasicTestEntity firstEntity = mock(BasicTestEntity.class);
        BasicTestEntity secondEntity = mock(BasicTestEntity.class);
        TestCategory category = mock(TestCategory.class);
        when(firstEntity.getCategory()).thenReturn(category);
        when(secondEntity.getCategory()).thenReturn(category);
        when(this.firstPort.read(id)).thenReturn(Optional.of(firstEntity));
        when(this.secondPort.read(id)).thenReturn(Optional.of(secondEntity));
        when(this.firstPort.supportsCategory(category)).thenReturn(true);
        when(this.secondPort.supportsCategory(category)).thenReturn(true);

        this.compositeDeleteUseCase.delete(id);

        verify(this.firstPort).read(id);
        verify(this.secondPort).read(id);
        verify(this.firstPort).supportsCategory(category);
        verify(this.secondPort).supportsCategory(category);
        verify(this.firstPort).delete(id);
        verify(this.secondPort).delete(id);
        verify(firstEntity).getCategory();
        verify(secondEntity).getCategory();
    }
}