package com.github.ryneal.domain.usecase.related.basic;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.entity.ParentTestEntity;
import com.github.ryneal.domain.usecase.ReadUseCase;
import com.github.ryneal.domain.usecase.UpdateUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicRelatedUpdateUseCaseTest {

    @Mock
    private ReadUseCase<BasicTestEntity, Integer> readUseCase;
    @Mock
    private UpdateUseCase<BasicTestEntity, Integer> updateUseCase;
    private BasicRelatedUpdateUseCase<BasicTestEntity, Integer, ParentTestEntity, String> useCase;

    @BeforeEach
    void setUp() {
        this.useCase = new BasicRelatedUpdateUseCase<>(this.readUseCase, this.updateUseCase);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.readUseCase, this.updateUseCase);
    }

    @Test
    void shouldReturnEmptyWhenEntityNotFound() {
        Integer id = 5;
        String parentId = "7";
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        when(this.readUseCase.read(id)).thenReturn(Optional.empty());

        Optional<BasicTestEntity> actual = this.useCase.update(parentId, id, updateEntity);

        assertThat(actual).isEmpty();
        verify(this.readUseCase).read(id);
        verifyNoMoreInteractions(updateEntity);
    }

    @Test
    void shouldReturnEmptyWhenParentNotFound() {
        Integer id = 5;
        String parentId = "7";
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        BasicTestEntity found = mock(BasicTestEntity.class);
        when(this.readUseCase.read(id)).thenReturn(Optional.of(found));
        when(found.getParent()).thenReturn(null);

        Optional<BasicTestEntity> actual = this.useCase.update(parentId, id, updateEntity);

        assertThat(actual).isEmpty();
        verify(this.readUseCase).read(id);
        verify(found).getParent();
        verifyNoMoreInteractions(updateEntity, found);
    }

    @Test
    void shouldReturnEmptyWhenParentIdDoesNotMatchRequest() {
        Integer id = 5;
        String parentId = "7";
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        BasicTestEntity found = mock(BasicTestEntity.class);
        ParentTestEntity parent = mock(ParentTestEntity.class);
        when(parent.getId()).thenReturn("non match");
        when(this.readUseCase.read(id)).thenReturn(Optional.of(found));
        when(found.getParent()).thenReturn(parent);

        Optional<BasicTestEntity> actual = this.useCase.update(parentId, id, updateEntity);

        assertThat(actual).isEmpty();
        verify(this.readUseCase).read(id);
        verify(found).getParent();
        verify(parent).getId();
        verifyNoMoreInteractions(updateEntity, found, parent);
    }

    @Test
    void shouldReturnEmptyWhenUpdateReturnsEmpty() {
        Integer id = 5;
        String parentId = "7";
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        BasicTestEntity found = mock(BasicTestEntity.class);
        ParentTestEntity parent = mock(ParentTestEntity.class);
        when(parent.getId()).thenReturn(parentId);
        when(this.readUseCase.read(id)).thenReturn(Optional.of(found));
        when(found.getParent()).thenReturn(parent);
        when(this.updateUseCase.update(id, updateEntity)).thenReturn(Optional.empty());

        Optional<BasicTestEntity> actual = this.useCase.update(parentId, id, updateEntity);

        assertThat(actual).isEmpty();
        verify(this.readUseCase).read(id);
        verify(found).getParent();
        verify(parent).getId();
        verify(this.updateUseCase).update(id, updateEntity);
        verifyNoMoreInteractions(updateEntity, found, parent);
    }

    @Test
    void shouldReturnPresentWhenUpdateReturnsPresent() {
        Integer id = 5;
        String parentId = "7";
        BasicTestEntity updateEntity = mock(BasicTestEntity.class);
        BasicTestEntity found = mock(BasicTestEntity.class);
        BasicTestEntity updatedEntity = mock(BasicTestEntity.class);
        ParentTestEntity parent = mock(ParentTestEntity.class);
        when(parent.getId()).thenReturn(parentId);
        when(this.readUseCase.read(id)).thenReturn(Optional.of(found));
        when(found.getParent()).thenReturn(parent);
        when(this.updateUseCase.update(id, updateEntity)).thenReturn(Optional.of(updatedEntity));

        Optional<BasicTestEntity> actual = this.useCase.update(parentId, id, updateEntity);

        assertThat(actual).hasValue(updatedEntity);
        verify(this.readUseCase).read(id);
        verify(found).getParent();
        verify(parent).getId();
        verify(this.updateUseCase).update(id, updateEntity);
        verifyNoMoreInteractions(updateEntity, found, parent);
    }

}