package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.entity.TestCategory;
import com.github.ryneal.domain.port.categorical.CategorizedReadPort;
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
class CompositeReadUseCaseTest {

    @Mock
    private CategorizedReadPort<BasicTestEntity, Integer, TestCategory> firstPort;
    @Mock
    private CategorizedReadPort<BasicTestEntity, Integer, TestCategory> secondPort;
    @Mock
    private TestCategory supportedCategory;
    private CompositeReadUseCase<BasicTestEntity, Integer, TestCategory> compositeReadUseCase;

    @BeforeEach
    void setUp() {
        this.compositeReadUseCase = new CompositeReadUseCase(Arrays.asList(this.firstPort, this.secondPort), Collections.singletonList(this.supportedCategory));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.firstPort, this.secondPort, this.supportedCategory);
    }

    @Test
    void shouldReturnEmptyWhenPortsDoNotSupportCategory() {
        when(this.firstPort.supportsCategory(this.supportedCategory)).thenReturn(false);
        when(this.secondPort.supportsCategory(this.supportedCategory)).thenReturn(false);

        Optional<BasicTestEntity> actual = this.compositeReadUseCase.read(1);

        assertThat(actual).isEmpty();
        verify(this.firstPort).supportsCategory(this.supportedCategory);
        verify(this.secondPort).supportsCategory(this.supportedCategory);
    }

    @Test
    void shouldReturnSecondResultWhenFirstPortReturnsEmpty() {
        Integer id = 1;
        BasicTestEntity expected = mock(BasicTestEntity.class);
        when(this.firstPort.supportsCategory(this.supportedCategory)).thenReturn(true);
        when(this.secondPort.supportsCategory(this.supportedCategory)).thenReturn(true);
        when(this.firstPort.read(id)).thenReturn(Optional.empty());
        when(this.secondPort.read(id)).thenReturn(Optional.of(expected));

        Optional<BasicTestEntity> actual = this.compositeReadUseCase.read(id);

        assertThat(actual).hasValue(expected);
        verify(this.firstPort).supportsCategory(this.supportedCategory);
        verify(this.secondPort).supportsCategory(this.supportedCategory);
        verify(this.firstPort).read(id);
        verify(this.secondPort).read(id);
    }
}