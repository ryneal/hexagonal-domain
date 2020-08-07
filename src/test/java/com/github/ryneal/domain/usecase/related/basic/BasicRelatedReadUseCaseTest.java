package com.github.ryneal.domain.usecase.related.basic;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.entity.ParentTestEntity;
import com.github.ryneal.domain.usecase.ReadUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicRelatedReadUseCaseTest {

    @Mock
    private ReadUseCase<BasicTestEntity, Integer> readUseCase;
    @InjectMocks
    private BasicRelatedReadUseCase<BasicTestEntity, Integer, ParentTestEntity, String> useCase;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.readUseCase);
    }

    @Test
    void shouldReturnEmptyWhenEntityNotFound() {
        Integer id = 2;
        String parentId = "umbrella";
        when(this.readUseCase.read(id)).thenReturn(Optional.empty());

        Optional<BasicTestEntity> actual = this.useCase.read(parentId, id);

        assertThat(actual).isEmpty();
        verify(this.readUseCase).read(id);
    }

    @Test
    void shouldReturnEmptyWhenEntityParentNotFound() {
        Integer id = 2;
        String parentId = "umbrella";
        BasicTestEntity expected = mock(BasicTestEntity.class);
        when(expected.getParent()).thenReturn(null);
        when(this.readUseCase.read(id)).thenReturn(Optional.of(expected));

        Optional<BasicTestEntity> actual = this.useCase.read(parentId, id);

        assertThat(actual).isEmpty();
        verify(expected).getParent();
        verify(this.readUseCase).read(id);
        verifyNoMoreInteractions(expected);
    }

    @Test
    void shouldReturnEmptyWhenEntityParentIdDoesntMatch() {
        Integer id = 2;
        String parentId = "umbrella";
        BasicTestEntity expected = mock(BasicTestEntity.class);
        ParentTestEntity parent = mock(ParentTestEntity.class);
        when(parent.getId()).thenReturn("academy");
        when(expected.getParent()).thenReturn(parent);
        when(this.readUseCase.read(id)).thenReturn(Optional.of(expected));

        Optional<BasicTestEntity> actual = this.useCase.read(parentId, id);

        assertThat(actual).isEmpty();
        verify(expected, times(2)).getParent();
        verify(parent).getId();
        verify(this.readUseCase).read(id);
        verifyNoMoreInteractions(expected, parent);
    }

    @Test
    void shouldReturnFoundWhenEntityParentIdMatch() {
        Integer id = 2;
        String parentId = "umbrella";
        BasicTestEntity expected = mock(BasicTestEntity.class);
        ParentTestEntity parent = mock(ParentTestEntity.class);
        when(parent.getId()).thenReturn(parentId);
        when(expected.getParent()).thenReturn(parent);
        when(this.readUseCase.read(id)).thenReturn(Optional.of(expected));

        Optional<BasicTestEntity> actual = this.useCase.read(parentId, id);

        assertThat(actual).hasValue(expected);
        verify(expected, times(2)).getParent();
        verify(parent).getId();
        verify(this.readUseCase).read(id);
        verifyNoMoreInteractions(expected, parent);
    }

}