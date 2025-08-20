package org.x96.sys.foundation.tokenizer.architecture.router.base;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.x96.sys.foundation.cs.lexer.token.Token;
import org.x96.sys.foundation.cs.lexer.tokenizer.Tokenizer;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.router.base.Router;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.router.base.Routing;

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

    @Test
    void testRoutingClassMethods() throws NoSuchFieldException, IllegalAccessException {
        Router r =
                new Router() {
                    @Override
                    public Token[] stream(Tokenizer tokenizer) {
                        return new Token[0];
                    }
                };
        assertNull(r.dispatcher);
        assertFalse(r.analyzed);
        var e = assertThrows(RuntimeException.class, () -> r.visitorTo(0x0));
        assertEquals("deve ser implementado na classe filha", e.getMessage());
    }
}
