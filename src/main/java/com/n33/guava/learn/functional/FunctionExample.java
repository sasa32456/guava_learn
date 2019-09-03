package com.n33.guava.learn.functional;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 还是直接用java8，。。。
 */
public class FunctionExample {

    public static void main(String[] args) throws IOException {
        Function<String, Integer> function = new Function<String, Integer>() {
            @Nullable
            @Override
            public Integer apply(@Nullable String input) {
                Preconditions.checkNotNull(input, "The input Stream should not be null");
                return input.length();
            }
        };


        System.out.println(function.apply("Hello"));

        process("Hello", new Handler.LengthDoubleHandler());

        System.out.println(Functions.toStringFunction().apply(new ServerSocket(1)));
    }

    interface Handler<IN, OUT> {
        OUT handle(IN input);

        class LengthDoubleHandler implements Handler<String, Integer>  {
            @Override
            public Integer handle(String input) {
                return input.length()*2;
            }
        }

    }

    private static void process(String text, Handler<String, Integer> handler) {
        System.out.println(handler.handle(text));
    }



}
