package org.x96.sys.foundation.tokenizer.architecture.router.implementations.serial;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.x96.sys.foundation.buzz.Buzz;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.implementations.serial.BuzzCantSerialize;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.implementations.serial.BuzzUnexpectedTokenForVisitor;
import org.x96.sys.foundation.io.ByteStream;
import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.router.implementations.serial.architecture.Quantifier;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.Terminal;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c0.Etx;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c0.Lf;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c0.Stx;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c3.DigitOne;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c3.DigitZero;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c6.LatinSmallLetterA;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c6.LatinSmallLetterC;
import org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c7.LatinSmallLetterS;
import org.x96.sys.foundation.tokenizer.token.Token;

class SerialTest {
    @Test
    void happyKnow() {
        // start empty
        Serial serial = new Serial();
        assertNotNull(serial.queued);
        assertEquals(0, serial.queued.size());

        // know record JUST_ONE
        serial.know(LatinSmallLetterC.class);
        assertEquals(1, serial.queued.size());
        assertEquals(LatinSmallLetterC.class, serial.visitorInLine().visitor);
        assertEquals(Quantifier.JUST_ONE, serial.visitorInLine().quantifier);

        // restart
        serial.clean();
        assertEquals(0, serial.queued.size());

        // one and know record JUST_ONE
        serial.one(LatinSmallLetterS.class);
        assertEquals(1, serial.queued.size());
        assertEquals(LatinSmallLetterS.class, serial.visitorInLine().visitor);
        assertEquals(Quantifier.JUST_ONE, serial.visitorInLine().quantifier);

        // visitorInLine respect FIFO order;
        serial.one(LatinSmallLetterS.class);
        assertEquals(2, serial.queued.size());
        assertEquals(LatinSmallLetterS.class, serial.visitorInLine().visitor);

        serial.clean();

        // ZERO_OR_ONE ?
        serial.zeroOrOne(Lf.class);
        assertEquals(1, serial.queued.size());
        assertEquals(Lf.class, serial.visitorInLine().visitor);
        assertEquals(Quantifier.ZERO_OR_ONE, serial.visitorInLine().quantifier);

        serial.clean();
        // ZERO_OR_MORE *
        serial.zeroOrMore(Stx.class);
        assertEquals(1, serial.queued.size());
        assertEquals(Stx.class, serial.visitorInLine().visitor);
        assertEquals(Quantifier.ZERO_OR_MORE, serial.visitorInLine().quantifier);

        serial.clean();
        // ONE_OR_MORE +
        serial.oneOrMore(Etx.class);
        assertEquals(1, serial.queued.size());
        assertEquals(Etx.class, serial.visitorInLine().visitor);
        assertEquals(Quantifier.ONE_OR_MORE, serial.visitorInLine().quantifier);
    }

    @Test
    void happyPrint() {
        Serial s = new Serial();
        assertEquals("- Current Serial [0]", s.toString());
        s.know(DigitZero.class);
        assertEquals("""
                - Current Serial [1]
                  0 => [JUST_ONE; DigitZero]""", s.toString());
        s.zeroOrOne(Lf.class);
        assertEquals("""
                - Current Serial [2]
                  0 => [JUST_ONE; DigitZero]
                  1 => [ZERO_OR_ONE; Lf]""", s.toString());
        s.zeroOrMore(DigitOne.class);
        assertEquals("""
                - Current Serial [3]
                  0 => [JUST_ONE; DigitZero]
                  1 => [ZERO_OR_ONE; Lf]
                  2 => [ZERO_OR_MORE; DigitOne]""", s.toString());
    }

    @Test
    void happySerialJustOne() {
        Serial serial = new Serial();
        serial.one(DigitZero.class);

        byte[] payload = new byte[]{0x30, 0x30, 0x30};
        ByteStream bs = ByteStream.raw(payload);
        Token[] tokens = serial.stream(new Tokenizer(bs));
        assertEquals(1, tokens.length);

        serial.clean();
        serial.one(DigitZero.class);
        serial.one(DigitZero.class);
        bs = ByteStream.raw(payload);
        tokens = serial.stream(new Tokenizer(bs));
        assertEquals(2, tokens.length);
    }

    @Test
    void happyStreamJustOneBuzz() {
        Serial serial = new Serial();
        serial.one(DigitZero.class);

        byte[] payload = new byte[]{0x31};
        ByteStream bs = ByteStream.raw(payload);
        Tokenizer tokenizer = new Tokenizer(bs);
        var e = assertThrows(BuzzCantSerialize.class, () -> serial.stream(tokenizer));
        assertTrue(tokenizer.ready());
        assertEquals(0, tokenizer.pointer());
        assertEquals(0x31, tokenizer.look());

        serial.clean();
        serial.one(DigitZero.class);
        serial.one(DigitOne.class);
        serial.one(DigitZero.class);
        Tokenizer t = new Tokenizer(
                ByteStream.raw(new byte[]{0x30, 0x31, 0x31})
        );
        e = assertThrows(
                BuzzCantSerialize.class,
                () -> serial.stream(
                        t
                )
        );
        assertEquals("""
                        🦕 [0x72]
                        🐝 [BuzzCantSerialize]
                        🌵 > Atual visitante [DigitZero] encontrou token [0x31] inesperado;
                          > Tokens esperados são [0x30]
                        """,
                e.getMessage().replaceAll("\u001B\\[[;\\d]*m", "")
        );
        assertTrue(t.ready());
        assertEquals(0x31, t.look());
        assertEquals(2, t.pointer());
    }

    @Test
    void happyStreamZeroOrOne() {
        Serial serial = new Serial();
        serial.zeroOrOne(DigitZero.class);

        byte[] payload = new byte[]{0x30, 0x30, 0x30};
        ByteStream bs = ByteStream.raw(payload);
        Token[] tokens = serial.stream(new Tokenizer(bs));
        assertEquals(1, tokens.length);

        serial.clean();
        serial.zeroOrOne(DigitOne.class);

        bs = ByteStream.raw(payload);
        tokens = serial.stream(new Tokenizer(bs));
        assertEquals(0, tokens.length);

        serial.clean();
        serial.zeroOrOne(DigitZero.class);
        serial.zeroOrOne(DigitZero.class);

        bs = ByteStream.raw(payload);
        tokens = serial.stream(new Tokenizer(bs));
        assertEquals(2, tokens.length);

        serial.clean();
        serial.zeroOrOne(DigitZero.class);
        serial.zeroOrOne(DigitZero.class);
        serial.zeroOrOne(DigitZero.class);

        bs = ByteStream.raw(payload);
        tokens = serial.stream(new Tokenizer(bs));
        assertEquals(3, tokens.length);
    }

    @Test
    void happySerialZeroOrMore() {
        Serial serial = new Serial();
        serial.zeroOrMore(LatinSmallLetterA.class);
        serial.zeroOrMore(DigitZero.class);
        serial.zeroOrMore(LatinSmallLetterC.class);

        ByteStream bs = ByteStream.raw(new byte[]{0x30, 0x30, 0x30});
        Token[] tokens = serial.stream(new Tokenizer(bs));
        assertEquals(3, tokens.length);

        serial.clean();

        serial.zeroOrMore(DigitZero.class);

        byte[] payload = new byte[]{0x30, 0x30, 0x30, 0x31, 0x31, 0x31};
        bs = ByteStream.raw(payload);
        tokens = serial.stream(new Tokenizer(bs));
        assertEquals(3, tokens.length);

        serial.clean();

        serial.zeroOrMore(DigitZero.class);
        serial.zeroOrMore(LatinSmallLetterC.class);
        serial.zeroOrMore(DigitOne.class);

        bs = ByteStream.raw(payload);
        tokens = serial.stream(new Tokenizer(bs));
        assertEquals(6, tokens.length);

        serial.clean();

        serial.zeroOrMore(LatinSmallLetterS.class);
        serial.zeroOrMore(DigitZero.class);
        serial.zeroOrMore(DigitOne.class);
        serial.zeroOrMore(LatinSmallLetterC.class);

        bs = ByteStream.raw(payload);
        tokens = serial.stream(new Tokenizer(bs));
        assertEquals(6, tokens.length);
    }

    @Test
    void happyOneOrMore() {
        Serial serial = new Serial();
        serial.oneOrMore(LatinSmallLetterS.class);
        byte[] payload = new byte[]{0x73, 0x63};
        Tokenizer tokenizer = new Tokenizer(ByteStream.raw(payload));
        Token[] tokens = serial.stream(tokenizer);
        assertEquals(1, tokens.length);

        serial.clean();
        serial.oneOrMore(LatinSmallLetterS.class);
        payload = new byte[]{0x73, 0x73, 0x63};
        tokens = serial.stream(new Tokenizer(ByteStream.raw(payload)));
        assertEquals(2, tokens.length);


        serial.clean();
        serial.oneOrMore(LatinSmallLetterS.class);
        serial.oneOrMore(LatinSmallLetterC.class);
        payload = new byte[]{0x73, 0x73, 0x63};
        tokens = serial.stream(new Tokenizer(ByteStream.raw(payload)));
        assertEquals(3, tokens.length);

        serial.clean();
        serial.oneOrMore(LatinSmallLetterS.class);
        serial.oneOrMore(LatinSmallLetterC.class);
        payload = new byte[]{0x73, 0x63, 0x63};
        tokens = serial.stream(new Tokenizer(ByteStream.raw(payload)));
        assertEquals(3, tokens.length);
    }

    @Test
    void happyOneOrMoreBuzzCantSerialize() {

        Serial serial = new Serial();
        serial.oneOrMore(DigitOne.class);
        var e =
                assertThrows(
                        BuzzCantSerialize.class,
                        () -> serial.stream(new Tokenizer(ByteStream.raw(new byte[]{0x30})))
                );
        String ex = """
                🦕 [0x72]
                🐝 [BuzzCantSerialize]
                🌵 > Atual visitante [DigitOne] encontrou token [0x30] inesperado;
                  > Tokens esperados são [0x31]
                """;
        String x = e.getMessage().replaceAll("\u001B\\[[;\\d]*m", "");
        assertEquals(ex,
                x
        );

        serial.clean();
        serial.oneOrMore(DigitZero.class);
        serial.oneOrMore(DigitOne.class);
        e = assertThrows(
                BuzzCantSerialize.class,
                () -> serial.stream(new Tokenizer(ByteStream.raw(new byte[]{0x30})))
        );
        assertEquals("""
                🦕 [0x72]
                🐝 [BuzzCantSerialize]
                🌵 > acabou a fita mas sobrou visitante""", e.getMessage());
    }

    @Test
    void happyStream() {
        Serial serial = new Serial();
        serial.oneOrMore(LatinSmallLetterS.class);

        byte[] payload = new byte[]{0x73, 0x63};

        Tokenizer tokenizer = new Tokenizer(ByteStream.raw(payload));
        Token[] tokens = serial.stream(tokenizer);
        assertEquals(1, tokens.length);
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{0:0 0}:{1:1 1}] }",
                tokens[0].toString());
        assertTrue(tokenizer.ready());
        assertEquals(1, tokenizer.pointer());

        // --------------------------------------------------------------------------------------------------
        serial.clean();
        serial.one(LatinSmallLetterC.class);
        tokens = serial.stream(tokenizer);
        assertEquals(1, tokens.length);
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:1 1}:{1:2 2}] }",
                tokens[0].toString());

        assertFalse(tokenizer.ready());
        assertEquals(2, tokenizer.pointer());

        // --------------------------------------------------------------------------------------------------
        serial.clean();
        serial.oneOrMore(LatinSmallLetterS.class);
        serial.one(LatinSmallLetterC.class);
        tokenizer = new Tokenizer(ByteStream.raw(payload));
        tokens = serial.stream(tokenizer);
        assertEquals(2, tokens.length);
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{0:0 0}:{1:1 1}] }",
                tokens[0].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:1 1}:{1:2 2}] }",
                tokens[1].toString());
        assertFalse(tokenizer.ready());
        assertEquals(2, tokenizer.pointer());

        // --------------------------------------------------------------------------------------------------
        serial.clean();
        serial.zeroOrOne(LatinSmallLetterS.class);
        serial.oneOrMore(LatinSmallLetterC.class);
        tokenizer = new Tokenizer(ByteStream.raw(payload));
        tokens = serial.stream(tokenizer);
        assertEquals(2, tokens.length);
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{0:0 0}:{1:1 1}] }",
                tokens[0].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:1 1}:{1:2 2}] }",
                tokens[1].toString());
        assertFalse(tokenizer.ready());
        assertEquals(2, tokenizer.pointer());

        // --------------------------------------------------------------------------------------------------
        payload = new byte[]{0x73, 0x73, 0x73, 0x63, 0x63, 0x63};
        // --------------------------------------------------------------------------------------------------
        serial.clean();
        serial.oneOrMore(LatinSmallLetterS.class);

        tokenizer = new Tokenizer(ByteStream.raw(payload));
        tokens = serial.stream(tokenizer);
        assertEquals(3, tokens.length);
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{0:0 0}:{1:1 1}] }",
                tokens[0].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{1:1 1}:{1:2 2}] }",
                tokens[1].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{1:2 2}:{1:3 3}] }",
                tokens[2].toString());
        assertTrue(tokenizer.ready());
        assertEquals(3, tokenizer.pointer());

        // --------------------------------------------------------------------------------------------------
        serial.clean();
        serial.oneOrMore(LatinSmallLetterC.class);
        tokens = serial.stream(tokenizer);
        assertEquals(3, tokens.length);
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:3 3}:{1:4 4}] }",
                tokens[0].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:4 4}:{1:5 5}] }",
                tokens[1].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:5 5}:{1:6 6}] }",
                tokens[2].toString());
        assertFalse(tokenizer.ready());
        assertEquals(6, tokenizer.pointer());

        // --------------------------------------------------------------------------------------------------
        serial.clean();
        serial.oneOrMore(LatinSmallLetterS.class);
        serial.zeroOrMore(LatinSmallLetterC.class);

        tokenizer = new Tokenizer(ByteStream.raw(payload));
        tokens = serial.stream(tokenizer);
        assertEquals(6, tokens.length);
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{0:0 0}:{1:1 1}] }",
                tokens[0].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{1:1 1}:{1:2 2}] }",
                tokens[1].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{1:2 2}:{1:3 3}] }",
                tokens[2].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:3 3}:{1:4 4}] }",
                tokens[3].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:4 4}:{1:5 5}] }",
                tokens[4].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:5 5}:{1:6 6}] }",
                tokens[5].toString());
        assertFalse(tokenizer.ready());
        assertEquals(6, tokenizer.pointer());

        // --------------------------------------------------------------------------------------------------
        serial.clean();
        serial.zeroOrMore(LatinSmallLetterS.class);
        serial.oneOrMore(LatinSmallLetterC.class);

        tokenizer = new Tokenizer(ByteStream.raw(payload));
        tokens = serial.stream(tokenizer);
        assertEquals(6, tokens.length);
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{0:0 0}:{1:1 1}] }",
                tokens[0].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{1:1 1}:{1:2 2}] }",
                tokens[1].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_S] Lexeme[0x73] Span[{1:2 2}:{1:3 3}] }",
                tokens[2].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:3 3}:{1:4 4}] }",
                tokens[3].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:4 4}:{1:5 5}] }",
                tokens[4].toString());
        assertEquals(
                "Token { Kind[LATIN_SMALL_LETTER_C] Lexeme[0x63] Span[{1:5 5}:{1:6 6}] }",
                tokens[5].toString());
        assertFalse(tokenizer.ready());
        assertEquals(6, tokenizer.pointer());
    }
}
