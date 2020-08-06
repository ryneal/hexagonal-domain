package com.github.ryneal.domain.usecase.related.basic;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.entity.ParentTestEntity;
import com.github.ryneal.domain.usecase.DeleteUseCase;
import com.github.ryneal.domain.usecase.ReadUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasicRelatedDeleteUseCaseTest {
    @Mock
    private ReadUseCase<BasicTestEntity, Integer> readUseCase;
    @Mock
    private DeleteUseCase<BasicTestEntity, Integer> deleteUseCase;
    private BasicRelatedDeleteUseCase<BasicTestEntity, Integer, ParentTestEntity, String> useCase;

    @BeforeEach
    void setUp() {
        this.useCase = new BasicRelatedDeleteUseCase<>(this.readUseCase, this.deleteUseCase);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.readUseCase, this.deleteUseCase);
    }

    @Test
    void shouldNotPerformActionWhenEntityNotFound() {
        String parentId = "parent";
        Integer id = 1;
        when(this.readUseCase.read(id)).thenReturn(Optional.empty());

        this.useCase.delete(parentId, id);

        verify(this.readUseCase).read(id);
    }

    @Test
    void shouldNotPerformActionWhenParentNotFound() {
        String parentId = "parent";
        Integer id = 1;
        BasicTestEntity found = mock(BasicTestEntity.class);
        when(found.getParent()).thenReturn(null);
        when(this.readUseCase.read(id)).thenReturn(Optional.of(found));

        this.useCase.delete(parentId, id);

        verify(this.readUseCase).read(id);
        verify(found).getParent();
        verifyNoMoreInteractions(found);
    }

    @Test
    void shouldNotPerformActionWhenParentNotEqualToParentId() {
        String parentId = "parent";
        String nonMatchParentId = "parent non match";
        Integer id = 1;
        BasicTestEntity found = mock(BasicTestEntity.class);
        ParentTestEntity parentTestEntity = mock(ParentTestEntity.class);
        when(parentTestEntity.getId()).thenReturn(nonMatchParentId);
        when(found.getParent()).thenReturn(parentTestEntity);
        when(this.readUseCase.read(id)).thenReturn(Optional.of(found));

        this.useCase.delete(parentId, id);

        verify(this.readUseCase).read(id);
        verify(found, times(2)).getParent();
        verify(parentTestEntity).getId();
        verifyNoMoreInteractions(found, parentTestEntity);
    }

    @Test
    void shouldPerformActionWhenParentEqualToParentId() {
        String parentId = "parent";
        Integer id = 1;
        BasicTestEntity found = mock(BasicTestEntity.class);
        ParentTestEntity parentTestEntity = mock(ParentTestEntity.class);
        when(parentTestEntity.getId()).thenReturn(parentId);
        when(found.getParent()).thenReturn(parentTestEntity);
        when(this.readUseCase.read(id)).thenReturn(Optional.of(found));

        this.useCase.delete(parentId, id);

        verify(this.readUseCase).read(id);
        verify(this.deleteUseCase).delete(id);
        verify(found, times(2)).getParent();
        verify(parentTestEntity).getId();
        verifyNoMoreInteractions(found, parentTestEntity);
    }

}