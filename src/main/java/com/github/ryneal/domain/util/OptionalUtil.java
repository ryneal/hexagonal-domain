package com.github.ryneal.domain.util;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public final class OptionalUtil {
    public static <T> UnaryOperator<T> peek(Consumer<T> c) {
        return x -> {
            c.accept(x);
            return x;
        };
    }
}
