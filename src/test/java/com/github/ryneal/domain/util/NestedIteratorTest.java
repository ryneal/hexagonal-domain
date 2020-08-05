package com.github.ryneal.domain.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NestedIteratorTest {

    @Test
    void shouldNotHaveNextWhenIteratorAndEmbeddedIteratorIsEmpty() {
        NestedIterator<String, Integer> iterator = new NestedIterator<>(Collections.emptyIterator(), s -> Collections.emptyIterator());

        boolean result = iterator.hasNext();

        assertFalse(result);
    }

    @Test
    void shouldHaveNextWhenIteratorHasNext() {
        NestedIterator<String, Integer> iterator = new NestedIterator<>(Arrays.asList("test", "test2").iterator(), s -> Collections.singletonList(2).iterator());

        Integer actual = iterator.next();
        boolean result = iterator.hasNext();

        assertTrue(result);
        assertEquals(2, actual);
    }

    @Test
    void shouldHaveNextWhenNestedIteratorHasNext() {
        NestedIterator<String, Integer> iterator = new NestedIterator<>(Arrays.asList("test").iterator(), s -> Arrays.asList(1, 2).iterator());

        Integer actual = iterator.next();
        boolean result = iterator.hasNext();
        Integer secondActual = iterator.next();

        assertTrue(result);
        assertEquals(1, actual);
        assertEquals(2, secondActual);
    }

    @Test
    void shouldThrowExceptionWhenDoesNotHaveNext() {
        NestedIterator<String, Integer> iterator = new NestedIterator<>(Collections.emptyIterator(), s -> Collections.emptyIterator());

        boolean result = iterator.hasNext();

        assertThrows(NoSuchElementException.class, () -> iterator.next());
        assertFalse(result);
    }

}