package org.x96.sys.foundation.tokenizer.architecture.router.base;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.token.Token;

import java.lang.reflect.Method;

class RoutingTest {

    @Test
    void testRoutingInterfaceMethods() throws Exception {
        Class<?> clazz = Routing.class;

        Method visitorTo = clazz.getMethod("visitorTo", int.class);
        assertEquals(Class.class, visitorTo.getReturnType());

        Method stream = clazz.getMethod("stream", Tokenizer.class);
        assertEquals(Token[].class, stream.getReturnType());

        Method analysis = clazz.getMethod("analysis");
        assertEquals(void.class, analysis.getReturnType());

        Method clean = clazz.getMethod("clean");
        assertEquals(void.class, clean.getReturnType());
    }
}
