package com.github.ryneal.domain.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public final class NestedIterator<T, U> implements Iterator<U> {

    private final Iterator<T> iterator;
    private final Function<T, Iterator<U>> function;

    private Iterator<U> embeddedIterator = Collections.emptyIterator();

    public NestedIterator(Iterator<T> iterator, Function<T, Iterator<U>> function) {
        this.iterator = iterator;
        this.function = function;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext() || this.embeddedIterator.hasNext();
    }

    @Override
    public U next() {
        if (this.embeddedIterator.hasNext()) {
            return this.embeddedIterator.next();
        } else if (this.iterator.hasNext()) {
            this.embeddedIterator = this.function.apply(this.iterator.next());
            return this.next();
        } else {
            throw new NoSuchElementException();
        }
    }
}
