package org.x96.sys.foundation.tokenizer.architecture.router.architecture;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzAmbiguousRouting;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzAmbiguousVisitor;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzAnalyzerEmpty;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzRouterVisitorsEmpty;
import org.x96.sys.foundation.cs.lexer.token.Token;
import org.x96.sys.foundation.cs.lexer.tokenizer.Tokenizer;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.router.architecture.Dispatcher;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.router.base.Router;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.visitor.implementations.terminals.Terminal;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.visitor.implementations.terminals.c0.Etx;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.visitor.implementations.terminals.c0.Stx;
import org.x96.sys.foundation.io.ByteStream;

class DispatcherTest {
    @Test
    void testAnalyzerConstructorDoesNotThrow() {
        Router r = new Router() {
            @Override
            public Token[] stream(Tokenizer tokenizer) {
                return new Token[0];
            }
        };
        r.clean();
        r.know(Stx.class);
        r.know(Etx.class);
        assertEquals(2, new Dispatcher(r).getTable().size());
    }

    @Test
    void happyToString() {
        Router r = new Router() {
            @Override
            public Token[] stream(Tokenizer tokenizer) {
                return new Token[0];
            }
        };
        r.clean();
        r.know(Stx.class);
        r.know(Etx.class);
        assertEquals(
                """
                        - Dispatcher Table [2]
                            [0x2] => org.x96.sys.foundation.cs.lexer.tokenizer.architecture.visitor.implementations.terminals.c0.Stx
                            [0x3] => org.x96.sys.foundation.cs.lexer.tokenizer.architecture.visitor.implementations.terminals.c0.Etx\
                        """,
                new Dispatcher(r).toString());
    }

    @Test
    void happyBuzzAmbiguousRouting() {
        Tokenizer tokenizer = new Tokenizer(ByteStream.raw(new byte[] { 0x2 }));
        assertTrue(new Terminal(tokenizer).allowed());
        assertTrue(new Stx(tokenizer).allowed());

        Router r = new Router() {
            @Override
            public Token[] stream(Tokenizer tokenizer) {
                return new Token[0];
            }
        };
        r.clean();
        r.know(Terminal.class);
        r.know(Stx.class);
        var e = assertThrows(BuzzAmbiguousRouting.class, () -> new Dispatcher(r));
        assertEquals(
                """
                        🦕 [0xE1]
                        🐝 [BuzzAmbiguousRouting]
                        🌵 > Ambiguidade detectada na fase de roteamento\
                        """,
                e.getMessage());
        assertNotNull(e.getCause());
        assertEquals(BuzzAmbiguousVisitor.class, e.getCause().getClass());
    }

    @Test
    void happyBuzzRouterVisitorsEmpty() {
        Router r = new Router() {
            @Override
            public Token[] stream(Tokenizer tokenizer) {
                return new Token[0];
            }
        };
        var e = assertThrows(BuzzRouterVisitorsEmpty.class, () -> new Dispatcher(r));
        assertEquals(
                """
                        🦕 [0xE2]
                        🐝 [BuzzRouterVisitorsEmpty]
                        🌵 > Nenhum visitante registrado\
                        """,
                e.getMessage());
        assertNotNull(e.getCause());
        assertEquals(BuzzAnalyzerEmpty.class, e.getCause().getClass());
    }
}
