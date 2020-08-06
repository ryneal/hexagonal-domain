package com.github.ryneal.domain.usecase.related.basic;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.entity.ParentTestEntity;
import com.github.ryneal.domain.usecase.CreateUseCase;
import com.github.ryneal.domain.usecase.ReadUseCase;
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
class BasicRelatedCreateUseCaseTest {

    @Mock
    private ReadUseCase<ParentTestEntity, String> parentReadUseCase;
    @Mock
    private CreateUseCase<BasicTestEntity, Integer> createUseCase;
    private BasicRelatedCreateUseCase<BasicTestEntity, Integer, ParentTestEntity, String> basicRelatedCreateUseCase;

    @BeforeEach
    void setUp() {
        this.basicRelatedCreateUseCase = new BasicRelatedCreateUseCase<>(this.parentReadUseCase, this.createUseCase);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.parentReadUseCase, this.createUseCase);
    }

    @Test
    void shouldReturnEmptyWhenParentIsNotFound() {
        String parentId = "test";
        BasicTestEntity createEntity = mock(BasicTestEntity.class);
        when(this.parentReadUseCase.read(parentId)).thenReturn(Optional.empty());

        Optional<BasicTestEntity> actual = this.basicRelatedCreateUseCase.create(parentId, createEntity);

        assertThat(actual).isEmpty();
        verify(this.parentReadUseCase).read(parentId);
        verifyNoMoreInteractions(createEntity);
    }

    @Test
    void shouldReturnEmptyWhenParentFoundAndChildIsNull() {
        String parentId = "test";
        ParentTestEntity parentEntity = mock(ParentTestEntity.class);
        when(this.parentReadUseCase.read(parentId)).thenReturn(Optional.of(parentEntity));

        Optional<BasicTestEntity> actual = this.basicRelatedCreateUseCase.create(parentId, null);

        assertThat(actual).isEmpty();
        verify(this.parentReadUseCase).read(parentId);
        verifyNoMoreInteractions(parentEntity);
    }

    @Test
    void shouldReturnEmptyWhenCreateReturnsEmpty() {
        String parentId = "test";
        BasicTestEntity createEntity = mock(BasicTestEntity.class);
        ParentTestEntity parentEntity = mock(ParentTestEntity.class);
        when(this.parentReadUseCase.read(parentId)).thenReturn(Optional.of(parentEntity));
        when(this.createUseCase.create(createEntity)).thenReturn(Optional.empty());

        Optional<BasicTestEntity> actual = this.basicRelatedCreateUseCase.create(parentId, createEntity);

        assertThat(actual).isEmpty();
        verify(this.parentReadUseCase).read(parentId);
        verify(this.createUseCase).create(createEntity);
        verify(createEntity).setParent(parentEntity);
        verifyNoMoreInteractions(parentEntity, createEntity);
    }

    @Test
    void shouldReturnCreatedEntityWhenCreateReturnsPresent() {
        String parentId = "test";
        BasicTestEntity createEntity = mock(BasicTestEntity.class);
        ParentTestEntity parentEntity = mock(ParentTestEntity.class);
        BasicTestEntity createdEntity = mock(BasicTestEntity.class);
        when(this.parentReadUseCase.read(parentId)).thenReturn(Optional.of(parentEntity));
        when(this.createUseCase.create(createEntity)).thenReturn(Optional.of(createdEntity));

        Optional<BasicTestEntity> actual = this.basicRelatedCreateUseCase.create(parentId, createEntity);

        assertThat(actual).hasValue(createdEntity);
        verify(this.parentReadUseCase).read(parentId);
        verify(this.createUseCase).create(createEntity);
        verify(createEntity).setParent(parentEntity);
        verifyNoMoreInteractions(parentEntity, createEntity, createdEntity);
    }
}