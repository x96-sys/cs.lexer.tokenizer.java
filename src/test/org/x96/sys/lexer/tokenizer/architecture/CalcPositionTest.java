package org.x96.sys.lexer.tokenizer.architecture;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.x96.sys.io.ByteStream;
import org.x96.sys.lexer.token.architecture.span.Position;

class CalcPositionTest {

    private static void test(byte[] payload, int offset, int expected_line, int expected_column) {
        CalcPosition cp = new CalcPosition(ByteStream.raw(payload));
        Position pos = cp.getPosition(offset);
        assertEquals(pos.column(), expected_column);
        assertEquals(pos.line(), expected_line);
        assertEquals(pos.offset(), offset);
    }

    @Test
    void happy() {
        byte[] payload =
                new byte[] {
                    0x2, 0xA, 0x61, 0xA, 0x62, 0x62, 0xA, 0x63, 0x63, 0x63, 0xA, 0x64, 0x20, 0x65,
                    0x66, 0xA, 0x3
                };
        test(payload, 0, 0, 0);

        test(payload, 1, 1, 1);

        test(payload, 2, 2, 1);
        test(payload, 3, 2, 2);

        test(payload, 4, 3, 1);
        test(payload, 5, 3, 2);
        test(payload, 6, 3, 3);

        test(payload, 7, 4, 1);
        test(payload, 8, 4, 2);
        test(payload, 9, 4, 3);
        test(payload, 10, 4, 4);

        test(payload, 11, 5, 1);
        test(payload, 12, 5, 2);
        test(payload, 13, 5, 3);
        test(payload, 14, 5, 4);
        test(payload, 15, 5, 5);
    }
}
