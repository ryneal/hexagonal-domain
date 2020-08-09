package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.port.DeletePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class CompositeDeleteUseCaseTest {

    @Mock
    private DeletePort<BasicTestEntity, Integer> firstPort;
    @Mock
    private DeletePort<BasicTestEntity, Integer> secondPort;
    private CompositeDeleteUseCase<BasicTestEntity, Integer> compositeDeleteUseCase;

    @BeforeEach
    void setUp() {
        this.compositeDeleteUseCase = new CompositeDeleteUseCase<>(Arrays.asList(this.firstPort, this.secondPort));
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
    void shouldPerformActionsWhenBothPortsCalled() {
        Integer id = Integer.MIN_VALUE;
        BasicTestEntity firstEntity = mock(BasicTestEntity.class);
        BasicTestEntity secondEntity = mock(BasicTestEntity.class);

        this.compositeDeleteUseCase.delete(id);

        verify(this.firstPort).delete(id);
        verify(this.secondPort).delete(id);
    }
}