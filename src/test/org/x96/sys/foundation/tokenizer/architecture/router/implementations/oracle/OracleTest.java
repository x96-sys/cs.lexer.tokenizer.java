package org.x96.sys.foundation.tokenizer.architecture.router.implementations.oracle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.x96.sys.foundation.io.ByteStream;
import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.Terminal;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c3.DigitZero;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c6.LatinSmallLetterC;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c6.LatinSmallLetterE;
import org.x96.sys.foundation.tokenizer.token.Token;

class OracleTest {

    @Test
    void happyOracle() {
        Oracle oracle = new Oracle();
        oracle.know(DigitZero.class);
        assertEquals(DigitZero.class, oracle.visitorTo(0x30));
        assertEquals(DigitZero.class, oracle.visitorTo(0x30));
        assertNull(oracle.visitorTo(0x31));
    }

    @Test
    void happyStream() {
        Oracle oracle = new Oracle();
        oracle.know(Terminal.class);
        assertEquals(Terminal.class, oracle.visitorTo((byte) 0x2));

        byte[] payload = new byte[] {0x63, 0x65, 0x63, 0x69, 0x73, 0x6F, 0x66, 0x69};
        Tokenizer tokenizer = new Tokenizer(ByteStream.raw(payload));

        Token[] tokens = oracle.stream(tokenizer);
        assertEquals(8, tokens.length);
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{0:0 0}:{1:1 1}] }",
                tokens[0].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_E] Lexeme[0x65] Span[{1:1 1}:{1:2 2}] }",
                tokens[1].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:2 2}:{1:3 3}] }",
                tokens[2].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_I] Lexeme[0x69] Span[{1:3 3}:{1:4 4}] }",
                tokens[3].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{1:4 4}:{1:5 5}] }",
                tokens[4].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_O] Lexeme[0x6F] Span[{1:5 5}:{1:6 6}] }",
                tokens[5].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_F] Lexeme[0x66] Span[{1:6 6}:{1:7 7}] }",
                tokens[6].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_I] Lexeme[0x69] Span[{1:7 7}:{1:8 8}] }",
                tokens[7].toString());


        oracle.clean();
        oracle.know(LatinSmallLetterC.class);
        assertEquals(1, oracle.stream(new Tokenizer(ByteStream.raw(payload))).length);

        oracle.clean();
        oracle.know(LatinSmallLetterE.class);
        oracle.know(LatinSmallLetterC.class);
        assertEquals(3, oracle.stream(new Tokenizer(ByteStream.raw(payload))).length);

    }
}
