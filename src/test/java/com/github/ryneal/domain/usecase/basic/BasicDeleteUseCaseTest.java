package com.github.ryneal.domain.usecase.basic;

import com.github.ryneal.domain.entity.BasicTestEntity;
import com.github.ryneal.domain.port.DeletePort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class BasicDeleteUseCaseTest {

    @Mock
    private DeletePort<BasicTestEntity, Integer> deletePort;
    @InjectMocks
    private BasicDeleteUseCase<BasicTestEntity, Integer> basicDeleteUseCase;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.deletePort);
    }

    @Test
    void shouldCallPortWithExpectedValue() {
        Integer id = mock(Integer.class);

        this.basicDeleteUseCase.delete(id);

        verify(this.deletePort).delete(id);
    }

}