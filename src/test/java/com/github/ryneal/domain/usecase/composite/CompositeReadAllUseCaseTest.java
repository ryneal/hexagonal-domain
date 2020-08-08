package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.entity.TestCategory;
import com.github.ryneal.domain.port.categorical.CategorizedReadAllPort;
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
    private TestCategory supportedCategory;
    @Mock
    private CategorizedReadAllPort<BasicTestEntity, Integer, TestCategory> firstPort;
    @Mock
    private CategorizedReadAllPort<BasicTestEntity, Integer, TestCategory> secondPort;
    private CompositeReadAllUseCase<BasicTestEntity, Integer, TestCategory> compositeReadAllUseCase;

    @BeforeEach
    void setUp() {
        this.compositeReadAllUseCase = new CompositeReadAllUseCase<>(Arrays.asList(this.firstPort, this.secondPort), Collections.singletonList(this.supportedCategory));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.firstPort, this.secondPort, this.supportedCategory);
    }

    @Test
    void shouldReturnEmptyWhenPortsDoNotSupportCategory() {
        when(this.firstPort.supportsCategory(this.supportedCategory)).thenReturn(false);
        when(this.secondPort.supportsCategory(this.supportedCategory)).thenReturn(false);

        List<BasicTestEntity> actual = this.compositeReadAllUseCase.readAll();

        assertThat(actual).isEmpty();
        verify(this.firstPort).supportsCategory(this.supportedCategory);
        verify(this.secondPort).supportsCategory(this.supportedCategory);
    }

    @Test
    void shouldReturnValuesFromBothPortsWhenPortsSupportCategory() {
        BasicTestEntity firstEntity = mock(BasicTestEntity.class);
        BasicTestEntity secondEntity = mock(BasicTestEntity.class);
        when(this.firstPort.supportsCategory(this.supportedCategory)).thenReturn(true);
        when(this.secondPort.supportsCategory(this.supportedCategory)).thenReturn(true);
        when(this.firstPort.readAll()).thenReturn(Collections.singletonList(firstEntity));
        when(this.secondPort.readAll()).thenReturn(Collections.singletonList(secondEntity));

        List<BasicTestEntity> actual = this.compositeReadAllUseCase.readAll();

        assertThat(actual)
                .contains(firstEntity)
                .contains(secondEntity)
                .hasSize(2);
        verify(this.firstPort).supportsCategory(this.supportedCategory);
        verify(this.secondPort).supportsCategory(this.supportedCategory);
        verify(this.firstPort).readAll();
        verify(this.secondPort).readAll();
    }

    @Test
    void shouldReturnValuesFromSecondPortWhenOnlySecondPortSupportsCategory() {
        BasicTestEntity secondEntity = mock(BasicTestEntity.class);
        when(this.firstPort.supportsCategory(this.supportedCategory)).thenReturn(false);
        when(this.secondPort.supportsCategory(this.supportedCategory)).thenReturn(true);
        when(this.secondPort.readAll()).thenReturn(Collections.singletonList(secondEntity));

        List<BasicTestEntity> actual = this.compositeReadAllUseCase.readAll();

        assertThat(actual)
                .contains(secondEntity)
                .hasSize(1);
        verify(this.firstPort).supportsCategory(this.supportedCategory);
        verify(this.secondPort).supportsCategory(this.supportedCategory);
        verify(this.secondPort).readAll();
    }

}