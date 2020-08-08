package com.github.ryneal.domain.usecase.basic;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.port.ReadAllPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicReadAllUseCaseTest {

    @Mock
    private ReadAllPort<BasicTestEntity, Integer> readAllPort;
    @InjectMocks
    private BasicReadAllUseCase<BasicTestEntity, Integer> basicReadAllUseCase;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.readAllPort);
    }

    @Test
    void shouldCallPortAndReturnResultWhenCalled() {
        List<BasicTestEntity> expected = Collections.singletonList(mock(BasicTestEntity.class));
        when(this.readAllPort.readAll()).thenReturn(expected);

        List<BasicTestEntity> actual = this.basicReadAllUseCase.readAll();

        assertThat(actual).isEqualTo(expected);
        verify(this.readAllPort).readAll();
    }
}