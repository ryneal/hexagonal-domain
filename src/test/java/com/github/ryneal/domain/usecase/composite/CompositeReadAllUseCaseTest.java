package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.port.ReadAllPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompositeReadAllUseCaseTest {

    @Mock
    private ReadAllPort<BasicTestEntity, Integer> firstPort;
    @Mock
    private ReadAllPort<BasicTestEntity, Integer> secondPort;
    private CompositeReadAllUseCase<BasicTestEntity, Integer> compositeReadAllUseCase;

    @BeforeEach
    void setUp() {
        this.compositeReadAllUseCase = new CompositeReadAllUseCase<>(Arrays.asList(this.firstPort, this.secondPort));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.firstPort, this.secondPort);
    }

    @Test
    void shouldReturnValuesFromBothPortsWhenPortsCalled() {
        BasicTestEntity firstEntity = mock(BasicTestEntity.class);
        BasicTestEntity secondEntity = mock(BasicTestEntity.class);

        List<BasicTestEntity> actual = this.compositeReadAllUseCase.readAll();

        assertThat(actual)
                .contains(firstEntity)
                .contains(secondEntity)
                .hasSize(2);
        verify(this.firstPort).readAll();
        verify(this.secondPort).readAll();
    }

}