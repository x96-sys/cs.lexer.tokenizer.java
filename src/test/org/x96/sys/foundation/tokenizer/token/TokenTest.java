package org.x96.sys.foundation.tokenizer.token;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.x96.sys.foundation.tokenizer.token.architecture.Lexeme;
import org.x96.sys.foundation.tokenizer.token.architecture.span.Position;
import org.x96.sys.foundation.tokenizer.token.architecture.span.Span;

class TokenTest {

    @Test
    void happy() {
        Lexeme lexeme = new Lexeme((byte) 0x61);
        Kind kind = Kind.LATIN_SMALL_LETTER_A;
        Position start = new Position(1, 1, 1);
        Position end = new Position(1, 2, 2);
        Span span = new Span(start, end);
        Token token = new Token(kind, lexeme, span);
        assertEquals("Token { Kind[LATIN_SMALL_LETTER_A] Lexeme[0x61] Span[{1:1 1}:{1:2 2}] }", token.toString());
    }
}
