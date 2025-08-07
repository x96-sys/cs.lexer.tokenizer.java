package org.x96.sys.foundation.tokenizer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.x96.sys.foundation.io.ByteStream;
import org.x96.sys.foundation.tokenizer.token.Kind;
import org.x96.sys.foundation.tokenizer.token.Token;
import org.x96.sys.foundation.tokenizer.token.architecture.span.Position;

import java.util.ArrayList;
import java.util.List;

class TokenizerTest {

    @Test
    void happyGetLine() {
        String input = "a\nb";
        Tokenizer tokenizer = new Tokenizer(ByteStream.raw(input.getBytes()));
        assertEquals("", tokenizer.getLineByNumber(0));
        assertEquals("a", tokenizer.getLineByNumber(1));
        assertEquals("b", tokenizer.getLineByNumber(2));
        assertEquals("", tokenizer.getLineByNumber(4));

        input = "a";
        tokenizer = new Tokenizer(ByteStream.raw(input.getBytes()));
        assertEquals("", tokenizer.getLineByNumber(0));
        assertEquals("a", tokenizer.getLineByNumber(1));
        assertEquals("", tokenizer.getLineByNumber(4));

        input =
                """
                example of text
                with multiples
                lines\
                """;
        tokenizer = new Tokenizer(ByteStream.wrapped(input.getBytes()));
        assertEquals("", tokenizer.getLineByNumber(0));
        assertEquals("\u0002example of text", tokenizer.getLineByNumber(1));
        assertEquals("with multiples", tokenizer.getLineByNumber(2));
        assertEquals("lines\u0003", tokenizer.getLineByNumber(3));
        assertEquals("", tokenizer.getLineByNumber(4));
    }

    @Test
    void happyOverKind() {
        Tokenizer tokenizer = new Tokenizer(ByteStream.raw(new byte[] {0x61}));
        assertEquals(1, tokenizer.length());
        assertTrue(tokenizer.ready());
        assertEquals(Kind.LATIN_SMALL_LETTER_A, tokenizer.kind());
        Token token = tokenizer.tokenize(Kind.UNKNOWN);
        assertEquals(Kind.UNKNOWN, token.kind());
        assertFalse(tokenizer.ready());
    }

    @Test
    void happyTokenize() {
        Tokenizer tokenizer = new Tokenizer(ByteStream.wrapped("abxy".getBytes()));
        assertEquals(6, tokenizer.length());
        assertEquals(0, tokenizer.pointer());

        Position position = tokenizer.position();
        assertEquals(0, position.column());
        assertEquals(0, position.line());
        assertEquals(0, position.offset());

        assertEquals(0x2, tokenizer.look());
        assertEquals(Kind.STX, tokenizer.kind());

        Token token = tokenizer.tokenize();
        assertEquals(1, tokenizer.pointer());
        assertEquals(0x2, token.lexeme().b());
        assertEquals(position, token.span().start());
    }

    @Test
    void happyPerformance() {
        int len = 0xFFFFF;
        byte[] payload = data(len);
        ByteStream byteStream = ByteStream.raw(payload);
        Tokenizer tokenizer = new Tokenizer(byteStream);
        List<Token> tokens = new ArrayList<>(len);

        long start = System.nanoTime();

        while (tokenizer.ready()) {
            tokens.add(tokenizer.tokenize());
        }

        long end = System.nanoTime();

        double durationSeconds = (end - start) / 1_000_000_000.0;
        double mbProcessed = len / (1024.0 * 1024.0);
        double mbps = mbProcessed / durationSeconds;

        System.out.printf(
                "✅ Tokens: %,d | Tempo: %.3f s | Velocidade: %.2f MB/s%n",
                tokens.size(), durationSeconds, mbps);

        assertTrue(mbps > 1.0, "💥 Performance abaixo do esperado (< 1 MB/s)");
    }

    public static byte[] data(int len) {
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = (byte) (i % 0x80); // 0x80 = 128
        }
        return result;
    }
}
