package org.x96.sys.foundation.buzz.tokenizer.architecture.router.implementations.serial;

import org.x96.sys.foundation.buzz.Buzz;
import org.x96.sys.foundation.io.ByteStream;
import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.factory.ReflectiveVisitorFactory;
import org.x96.sys.foundation.tokenizer.architecture.router.implementations.serial.architecture.Step;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BuzzCantSerialize extends Buzz {
    public static final int CODE = 0x72;

    public BuzzCantSerialize(Tokenizer tokenizer, Step step, Buzz causa) {
        super(CODE, BuzzCantSerialize.class.getSimpleName(), explain(tokenizer, step), causa);
    }

    public BuzzCantSerialize(Step step) {
        super(CODE, BuzzCantSerialize.class.getSimpleName(), "acabou a fita mas sobrou visitante");
    }

    private static String explain(Tokenizer tokenizer, Step step) {
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";
        final String RESET = "\u001B[0m";

        return String.format(
                """
                Atual visitante [%s] encontrou token [%s0x%X%s] inesperado;
                  > Tokens esperados são [%s]
                """,
                step.visitor.getSimpleName(),
                RED,
                tokenizer.look(),
                RESET,
                discovery(step.visitor).stream()
                        .map(i -> GREEN + String.format("0x%X", i) + RESET)
                        .collect(Collectors.joining(", ")));
    }

    private static List<Integer> discovery(Class<? extends Visitor> v) {
        List<Integer> r = new ArrayList<>();
        for (int i = 0; i < 0xFF; i++) {
            Tokenizer t = new Tokenizer(ByteStream.raw(new byte[] {(byte) i}));
            if (ReflectiveVisitorFactory.happens(v, t).allowed()) {
                r.add(i);
            }
        }
        return r;
    }
}
