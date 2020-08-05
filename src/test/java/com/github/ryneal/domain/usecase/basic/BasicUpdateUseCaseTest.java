package com.github.ryneal.domain.usecase.basic;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.port.UpdatePort;
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
class BasicUpdateUseCaseTest {

    @Mock
    private UpdatePort<BasicTestEntity, Integer> updatePort;
    @InjectMocks
    private BasicUpdateUseCase<BasicTestEntity, Integer> basicUpdateUseCase;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.updatePort);
    }

    @Test
    void shouldReturnEmptyWhenCannotReadEntity() {
        Integer id = mock(Integer.class);
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        when(this.updatePort.read(id)).thenReturn(Optional.empty());

        Optional<BasicTestEntity> actual = this.basicUpdateUseCase.update(id, updateEntity);

        assertThat(actual).isEmpty();
        verify(this.updatePort).read(id);
    }

    @Test
    void shouldReturnUpdatedEntityWhenEntityIsRead() {
        Integer id = mock(Integer.class);
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        BasicTestEntity expected = mock(BasicTestEntity.class);
        when(this.updatePort.read(id)).thenReturn(Optional.of(expected));
        when(this.updatePort.update(id, updateEntity)).thenReturn(Optional.of(expected));

        Optional<BasicTestEntity> actual = this.basicUpdateUseCase.update(id, updateEntity);

        assertThat(actual).hasValue(expected);
        verify(this.updatePort).read(id);
        verify(this.updatePort).update(id, updateEntity);
    }
}