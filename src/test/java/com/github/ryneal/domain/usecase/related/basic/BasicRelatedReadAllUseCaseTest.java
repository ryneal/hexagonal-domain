package com.github.ryneal.domain.usecase.related.basic;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.entity.ParentTestEntity;
import com.github.ryneal.domain.usecase.ReadAllUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicRelatedReadAllUseCaseTest {

    @Mock
    private ReadAllUseCase<BasicTestEntity, Integer> readUseCase;
    @InjectMocks
    private BasicRelatedReadAllUseCase<BasicTestEntity, Integer, ParentTestEntity, String> useCase;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.readUseCase);
    }

    @Test
    void shouldReturnEmptyWhenEntityNotFound() {
        String parentId = "umbrella";
        when(this.readUseCase.readAll()).thenReturn(Collections.emptyList());

        List<BasicTestEntity> actual = this.useCase.readAll(parentId);

        assertThat(actual).isEmpty();
        verify(this.readUseCase).readAll();
    }

    @Test
    void shouldReturnEmptyWhenEntityParentNotFound() {
        String parentId = "umbrella";
        BasicTestEntity expected = mock(BasicTestEntity.class);
        when(expected.getParent()).thenReturn(null);
        when(this.readUseCase.readAll()).thenReturn(Collections.singletonList(expected));

        List<BasicTestEntity> actual = this.useCase.readAll(parentId);

        assertThat(actual).isEmpty();
        verify(expected).getParent();
        verify(this.readUseCase).readAll();
        verifyNoMoreInteractions(expected);
    }

    @Test
    void shouldReturnEmptyWhenEntityParentIdDoesntMatch() {
        String parentId = "umbrella";
        BasicTestEntity expected = mock(BasicTestEntity.class);
        ParentTestEntity parent = mock(ParentTestEntity.class);
        when(parent.getId()).thenReturn("academy");
        when(expected.getParent()).thenReturn(parent);
        when(this.readUseCase.readAll()).thenReturn(Collections.singletonList(expected));

        List<BasicTestEntity> actual = this.useCase.readAll(parentId);

        assertThat(actual).isEmpty();
        verify(expected, times(2)).getParent();
        verify(parent).getId();
        verify(this.readUseCase).readAll();
        verifyNoMoreInteractions(expected, parent);
    }

    @Test
    void shouldReturnFoundWhenEntityParentIdMatch() {
        String parentId = "umbrella";
        BasicTestEntity expected = mock(BasicTestEntity.class);
        ParentTestEntity parent = mock(ParentTestEntity.class);
        when(parent.getId()).thenReturn(parentId);
        when(expected.getParent()).thenReturn(parent);
        when(this.readUseCase.readAll()).thenReturn(Collections.singletonList(expected));

        List<BasicTestEntity> actual = this.useCase.readAll(parentId);

        assertThat(actual).contains(expected).hasSize(1);
        verify(expected, times(2)).getParent();
        verify(parent).getId();
        verify(this.readUseCase).readAll();
        verifyNoMoreInteractions(expected, parent);
    }

}