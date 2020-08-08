package com.github.ryneal.domain.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public final class NestedIterator<T, U> implements Iterator<U> {

    private final Iterator<T> iterator;
    private final Function<T, Iterator<U>> function;

    private Iterator<U> nestedIterator = Collections.emptyIterator();

    public NestedIterator(Iterator<T> iterator, Function<T, Iterator<U>> function) {
        this.iterator = iterator;
        this.function = function;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext() || this.nestedIterator.hasNext();
    }

    @Override
    public U next() {
        if (this.nestedIterator.hasNext()) {
            return this.nestedIterator.next();
        } else if (this.iterator.hasNext()) {
            this.nestedIterator = this.function.apply(this.iterator.next());
            return this.next();
        } else {
            throw new NoSuchElementException();
        }
    }
}
