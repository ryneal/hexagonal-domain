package com.github.ryneal.domain.usecase.basic;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.port.ReadPort;
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
class BasicReadUseCaseTest {

    @Mock
    private ReadPort<BasicTestEntity, Integer> readPort;
    @InjectMocks
    private BasicReadUseCase<BasicTestEntity, Integer> basicReadUseCase;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.readPort);
    }

    @Test
    void shouldCallPortAndReturnResultWhenCalled() {
        Integer id = mock(Integer.class);
        BasicTestEntity expected = mock(BasicTestEntity.class);
        when(this.readPort.read(id)).thenReturn(Optional.of(expected));

        Optional<BasicTestEntity> actual = this.basicReadUseCase.read(id);

        assertThat(actual).hasValue(expected);
        verify(this.readPort).read(id);
    }
}