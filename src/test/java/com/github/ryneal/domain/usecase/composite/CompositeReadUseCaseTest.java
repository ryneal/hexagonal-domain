package com.github.ryneal.domain.usecase.composite;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.port.ReadPort;
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
class CompositeReadUseCaseTest {

    @Mock
    private ReadPort<BasicTestEntity, Integer> firstPort;
    @Mock
    private ReadPort<BasicTestEntity, Integer> secondPort;

    private CompositeReadUseCase<BasicTestEntity, Integer> compositeReadUseCase;

    @BeforeEach
    void setUp() {
        this.compositeReadUseCase = new CompositeReadUseCase(Arrays.asList(this.firstPort, this.secondPort));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.firstPort, this.secondPort);
    }

    @Test
    void shouldReturnSecondResultWhenFirstPortReturnsEmpty() {
        Integer id = 1;
        BasicTestEntity expected = mock(BasicTestEntity.class);
        when(this.firstPort.read(id)).thenReturn(Optional.empty());
        when(this.secondPort.read(id)).thenReturn(Optional.of(expected));

        Optional<BasicTestEntity> actual = this.compositeReadUseCase.read(id);

        assertThat(actual).hasValue(expected);
        verify(this.firstPort).read(id);
        verify(this.secondPort).read(id);
    }
}