package org.x96.sys.foundation.tokenizer.token;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class KindTest {
    @Test
    void happyIs() {

        assertEquals(Kind.is((byte) -1), Kind.UNKNOWN);

        assertEquals(Kind.is((byte) 0x0), Kind.NULL);
        assertEquals(Kind.is((byte) 0x0), Kind.NULL);
        assertEquals(Kind.is((byte) 0x1), Kind.SOH);
        assertEquals(Kind.is((byte) 0x2), Kind.STX);
        assertEquals(Kind.is((byte) 0x3), Kind.ETX);
        assertEquals(Kind.is((byte) 0x4), Kind.EOT);
        assertEquals(Kind.is((byte) 0x5), Kind.ENQ);
        assertEquals(Kind.is((byte) 0x6), Kind.ACK);
        assertEquals(Kind.is((byte) 0x7), Kind.BEL);
        assertEquals(Kind.is((byte) 0x8), Kind.BS);
        assertEquals(Kind.is((byte) 0x9), Kind.HT);
        assertEquals(Kind.is((byte) 0xA), Kind.LF);
        assertEquals(Kind.is((byte) 0xB), Kind.VT);
        assertEquals(Kind.is((byte) 0xC), Kind.FF);
        assertEquals(Kind.is((byte) 0xD), Kind.CR);
        assertEquals(Kind.is((byte) 0xE), Kind.SO);
        assertEquals(Kind.is((byte) 0xF), Kind.SI);

        assertEquals(Kind.is((byte) 0x10), Kind.DLE);
        assertEquals(Kind.is((byte) 0x11), Kind.DC1);
        assertEquals(Kind.is((byte) 0x12), Kind.DC2);
        assertEquals(Kind.is((byte) 0x13), Kind.DC3);
        assertEquals(Kind.is((byte) 0x14), Kind.DC4);
        assertEquals(Kind.is((byte) 0x15), Kind.NAK);
        assertEquals(Kind.is((byte) 0x16), Kind.SYN);
        assertEquals(Kind.is((byte) 0x17), Kind.ETB);
        assertEquals(Kind.is((byte) 0x18), Kind.CAN);
        assertEquals(Kind.is((byte) 0x19), Kind.EM);
        assertEquals(Kind.is((byte) 0x1A), Kind.SUB);
        assertEquals(Kind.is((byte) 0x1B), Kind.ESC);
        assertEquals(Kind.is((byte) 0x1C), Kind.FS);
        assertEquals(Kind.is((byte) 0x1D), Kind.GS);
        assertEquals(Kind.is((byte) 0x1E), Kind.RS);
        assertEquals(Kind.is((byte) 0x1F), Kind.US);

        assertEquals(Kind.is((byte) 0x20), Kind.SPACE);
        assertEquals(Kind.is((byte) 0x21), Kind.EXCLAMATION_MARK);
        assertEquals(Kind.is((byte) 0x22), Kind.QUOTATION_MARK);
        assertEquals(Kind.is((byte) 0x23), Kind.NUMBER_SIGN);
        assertEquals(Kind.is((byte) 0x24), Kind.DOLLAR_SIGN);
        assertEquals(Kind.is((byte) 0x25), Kind.PERCENT_SIGN);
        assertEquals(Kind.is((byte) 0x26), Kind.AMPERSAND);
        assertEquals(Kind.is((byte) 0x27), Kind.APOSTROPHE);
        assertEquals(Kind.is((byte) 0x28), Kind.LEFT_PARENTHESIS);
        assertEquals(Kind.is((byte) 0x29), Kind.RIGHT_PARENTHESIS);
        assertEquals(Kind.is((byte) 0x2A), Kind.ASTERISK);
        assertEquals(Kind.is((byte) 0x2B), Kind.PLUS);
        assertEquals(Kind.is((byte) 0x2C), Kind.COMMA);
        assertEquals(Kind.is((byte) 0x2D), Kind.HYPHEN_MINUS);
        assertEquals(Kind.is((byte) 0x2E), Kind.FULL_STOP);
        assertEquals(Kind.is((byte) 0x2F), Kind.SOLIDUS);

        assertEquals(Kind.is((byte) 0x30), Kind.DIGIT_ZERO);
        assertEquals(Kind.is((byte) 0x31), Kind.DIGIT_ONE);
        assertEquals(Kind.is((byte) 0x32), Kind.DIGIT_TWO);
        assertEquals(Kind.is((byte) 0x33), Kind.DIGIT_THREE);
        assertEquals(Kind.is((byte) 0x34), Kind.DIGIT_FOUR);
        assertEquals(Kind.is((byte) 0x35), Kind.DIGIT_FIVE);
        assertEquals(Kind.is((byte) 0x36), Kind.DIGIT_SIX);
        assertEquals(Kind.is((byte) 0x37), Kind.DIGIT_SEVEN);
        assertEquals(Kind.is((byte) 0x38), Kind.DIGIT_EIGHT);
        assertEquals(Kind.is((byte) 0x39), Kind.DIGIT_NINE);
        assertEquals(Kind.is((byte) 0x3A), Kind.COLON);
        assertEquals(Kind.is((byte) 0x3B), Kind.SEMICOLON);
        assertEquals(Kind.is((byte) 0x3C), Kind.LESS_THAN_SIGN);
        assertEquals(Kind.is((byte) 0x3D), Kind.EQUALS);
        assertEquals(Kind.is((byte) 0x3E), Kind.GREATER_THAN_SIGN);
        assertEquals(Kind.is((byte) 0x3F), Kind.QUESTION_MARK);

        assertEquals(Kind.is((byte) 0x40), Kind.COMMERCIAL_AT);
        assertEquals(Kind.is((byte) 0x41), Kind.LATIN_CAPITAL_LETTER_A);
        assertEquals(Kind.is((byte) 0x42), Kind.LATIN_CAPITAL_LETTER_B);
        assertEquals(Kind.is((byte) 0x43), Kind.LATIN_CAPITAL_LETTER_C);
        assertEquals(Kind.is((byte) 0x44), Kind.LATIN_CAPITAL_LETTER_D);
        assertEquals(Kind.is((byte) 0x45), Kind.LATIN_CAPITAL_LETTER_E);
        assertEquals(Kind.is((byte) 0x46), Kind.LATIN_CAPITAL_LETTER_F);
        assertEquals(Kind.is((byte) 0x47), Kind.LATIN_CAPITAL_LETTER_G);
        assertEquals(Kind.is((byte) 0x48), Kind.LATIN_CAPITAL_LETTER_H);
        assertEquals(Kind.is((byte) 0x49), Kind.LATIN_CAPITAL_LETTER_I);
        assertEquals(Kind.is((byte) 0x4A), Kind.LATIN_CAPITAL_LETTER_J);
        assertEquals(Kind.is((byte) 0x4B), Kind.LATIN_CAPITAL_LETTER_K);
        assertEquals(Kind.is((byte) 0x4C), Kind.LATIN_CAPITAL_LETTER_L);
        assertEquals(Kind.is((byte) 0x4D), Kind.LATIN_CAPITAL_LETTER_M);
        assertEquals(Kind.is((byte) 0x4E), Kind.LATIN_CAPITAL_LETTER_N);
        assertEquals(Kind.is((byte) 0x4F), Kind.LATIN_CAPITAL_LETTER_O);

        assertEquals(Kind.is((byte) 0x50), Kind.LATIN_CAPITAL_LETTER_P);
        assertEquals(Kind.is((byte) 0x51), Kind.LATIN_CAPITAL_LETTER_Q);
        assertEquals(Kind.is((byte) 0x52), Kind.LATIN_CAPITAL_LETTER_R);
        assertEquals(Kind.is((byte) 0x53), Kind.LATIN_CAPITAL_LETTER_S);
        assertEquals(Kind.is((byte) 0x54), Kind.LATIN_CAPITAL_LETTER_T);
        assertEquals(Kind.is((byte) 0x55), Kind.LATIN_CAPITAL_LETTER_U);
        assertEquals(Kind.is((byte) 0x56), Kind.LATIN_CAPITAL_LETTER_V);
        assertEquals(Kind.is((byte) 0x57), Kind.LATIN_CAPITAL_LETTER_W);
        assertEquals(Kind.is((byte) 0x58), Kind.LATIN_CAPITAL_LETTER_X);
        assertEquals(Kind.is((byte) 0x59), Kind.LATIN_CAPITAL_LETTER_Y);
        assertEquals(Kind.is((byte) 0x5A), Kind.LATIN_CAPITAL_LETTER_Z);
        assertEquals(Kind.is((byte) 0x5B), Kind.LEFT_SQUARE_BRACKET);
        assertEquals(Kind.is((byte) 0x5C), Kind.REVERSE_SOLIDUS);
        assertEquals(Kind.is((byte) 0x5D), Kind.RIGHT_SQUARE_BRACKET);
        assertEquals(Kind.is((byte) 0x5E), Kind.CIRCUMFLEX_ACCENT);
        assertEquals(Kind.is((byte) 0x5F), Kind.LOW_LINE);

        assertEquals(Kind.is((byte) 0x60), Kind.GRAVE_ACCENT);
        assertEquals(Kind.is((byte) 0x61), Kind.LATIN_SMALL_LETTER_A);
        assertEquals(Kind.is((byte) 0x62), Kind.LATIN_SMALL_LETTER_B);
        assertEquals(Kind.is((byte) 0x63), Kind.LATIN_SMALL_LETTER_C);
        assertEquals(Kind.is((byte) 0x64), Kind.LATIN_SMALL_LETTER_D);
        assertEquals(Kind.is((byte) 0x65), Kind.LATIN_SMALL_LETTER_E);
        assertEquals(Kind.is((byte) 0x66), Kind.LATIN_SMALL_LETTER_F);
        assertEquals(Kind.is((byte) 0x67), Kind.LATIN_SMALL_LETTER_G);
        assertEquals(Kind.is((byte) 0x68), Kind.LATIN_SMALL_LETTER_H);
        assertEquals(Kind.is((byte) 0x69), Kind.LATIN_SMALL_LETTER_I);
        assertEquals(Kind.is((byte) 0x6A), Kind.LATIN_SMALL_LETTER_J);
        assertEquals(Kind.is((byte) 0x6B), Kind.LATIN_SMALL_LETTER_K);
        assertEquals(Kind.is((byte) 0x6C), Kind.LATIN_SMALL_LETTER_L);
        assertEquals(Kind.is((byte) 0x6D), Kind.LATIN_SMALL_LETTER_M);
        assertEquals(Kind.is((byte) 0x6E), Kind.LATIN_SMALL_LETTER_N);
        assertEquals(Kind.is((byte) 0x6F), Kind.LATIN_SMALL_LETTER_O);

        assertEquals(Kind.is((byte) 0x70), Kind.LATIN_SMALL_LETTER_P);
        assertEquals(Kind.is((byte) 0x71), Kind.LATIN_SMALL_LETTER_Q);
        assertEquals(Kind.is((byte) 0x72), Kind.LATIN_SMALL_LETTER_R);
        assertEquals(Kind.is((byte) 0x73), Kind.LATIN_SMALL_LETTER_S);
        assertEquals(Kind.is((byte) 0x74), Kind.LATIN_SMALL_LETTER_T);
        assertEquals(Kind.is((byte) 0x75), Kind.LATIN_SMALL_LETTER_U);
        assertEquals(Kind.is((byte) 0x76), Kind.LATIN_SMALL_LETTER_V);
        assertEquals(Kind.is((byte) 0x77), Kind.LATIN_SMALL_LETTER_W);
        assertEquals(Kind.is((byte) 0x78), Kind.LATIN_SMALL_LETTER_X);
        assertEquals(Kind.is((byte) 0x79), Kind.LATIN_SMALL_LETTER_Y);
        assertEquals(Kind.is((byte) 0x7A), Kind.LATIN_SMALL_LETTER_Z);
        assertEquals(Kind.is((byte) 0x7B), Kind.LEFT_CURLY_BRACKET);
        assertEquals(Kind.is((byte) 0x7C), Kind.VERTICAL_LINE);
        assertEquals(Kind.is((byte) 0x7D), Kind.RIGHT_CURLY_BRACKET);
        assertEquals(Kind.is((byte) 0x7E), Kind.TILDE);
        assertEquals(Kind.is((byte) 0x7F), Kind.DELETE);
        assertEquals(Kind.is((byte) 0x80), Kind.UNKNOWN);
        assertEquals(Kind.is((byte) 0x81), Kind.UNKNOWN);

    }

    @Test
    void happyIsNull() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x0)
                assertTrue(Kind.isNull((byte) i));
            if (i != 0x0)
                assertFalse(Kind.isNull((byte) i));
        }
    }

    @Test
    void happyIsSoh() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x1)
                assertTrue(Kind.isSoh((byte) i));
            if (i != 0x1)
                assertFalse(Kind.isSoh((byte) i));
        }
    }

    @Test
    void happyIsStx() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x2)
                assertTrue(Kind.isStx((byte) i));
            if (i != 0x2)
                assertFalse(Kind.isStx((byte) i));
        }
    }

    @Test
    void happyIsEtx() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x3)
                assertTrue(Kind.isEtx((byte) i));
            if (i != 0x3)
                assertFalse(Kind.isEtx((byte) i));
        }
    }

    @Test
    void happyIsEot() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x4)
                assertTrue(Kind.isEot((byte) i));
            if (i != 0x4)
                assertFalse(Kind.isEot((byte) i));
        }
    }

    @Test
    void happyIsEnq() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x5)
                assertTrue(Kind.isEnq((byte) i));
            if (i != 0x5)
                assertFalse(Kind.isEnq((byte) i));
        }
    }

    @Test
    void happyIsAck() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x6)
                assertTrue(Kind.isAck((byte) i));
            if (i != 0x6)
                assertFalse(Kind.isAck((byte) i));
        }
    }

    @Test
    void happyIsBel() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x7)
                assertTrue(Kind.isBel((byte) i));
            if (i != 0x7)
                assertFalse(Kind.isBel((byte) i));
        }
    }

    @Test
    void happyIsBs() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x8)
                assertTrue(Kind.isBs((byte) i));
            if (i != 0x8)
                assertFalse(Kind.isBs((byte) i));
        }
    }

    @Test
    void happyIsHt() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x9)
                assertTrue(Kind.isHt((byte) i));
            if (i != 0x9)
                assertFalse(Kind.isHt((byte) i));
        }
    }

    @Test
    void happyIsLf() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0xA)
                assertTrue(Kind.isLf((byte) i));
            if (i != 0xA)
                assertFalse(Kind.isLf((byte) i));
        }
    }

    @Test
    void happyIsVt() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0xB)
                assertTrue(Kind.isVt((byte) i));
            if (i != 0xB)
                assertFalse(Kind.isVt((byte) i));
        }
    }

    @Test
    void happyIsFf() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0xC)
                assertTrue(Kind.isFf((byte) i));
            if (i != 0xC)
                assertFalse(Kind.isFf((byte) i));
        }
    }

    @Test
    void happyIsCr() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0xD)
                assertTrue(Kind.isCr((byte) i));
            if (i != 0xD)
                assertFalse(Kind.isCr((byte) i));
        }
    }

    @Test
    void happyIsSo() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0xE)
                assertTrue(Kind.isSo((byte) i));
            if (i != 0xE)
                assertFalse(Kind.isSo((byte) i));
        }
    }

    @Test
    void happyIsSi() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0xF)
                assertTrue(Kind.isSi((byte) i));
            if (i != 0xF)
                assertFalse(Kind.isSi((byte) i));
        }
    }

    @Test
    void happyIsDle() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x10)
                assertTrue(Kind.isDle((byte) i));
            if (i != 0x10)
                assertFalse(Kind.isDle((byte) i));
        }
    }

    @Test
    void happyIsDc1() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x11)
                assertTrue(Kind.isDc1((byte) i));
            if (i != 0x11)
                assertFalse(Kind.isDc1((byte) i));
        }
    }

    @Test
    void happyIsDc2() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x12)
                assertTrue(Kind.isDc2((byte) i));
            if (i != 0x12)
                assertFalse(Kind.isDc2((byte) i));
        }
    }

    @Test
    void happyIsDc3() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x13)
                assertTrue(Kind.isDc3((byte) i));
            if (i != 0x13)
                assertFalse(Kind.isDc3((byte) i));
        }
    }

    @Test
    void happyIsDc4() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x14)
                assertTrue(Kind.isDc4((byte) i));
            if (i != 0x14)
                assertFalse(Kind.isDc4((byte) i));
        }
    }

    @Test
    void happyIsNak() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x15)
                assertTrue(Kind.isNak((byte) i));
            if (i != 0x15)
                assertFalse(Kind.isNak((byte) i));
        }
    }

    @Test
    void happyIsSyn() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x16)
                assertTrue(Kind.isSyn((byte) i));
            if (i != 0x16)
                assertFalse(Kind.isSyn((byte) i));
        }
    }

    @Test
    void happyIsEtb() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x17)
                assertTrue(Kind.isEtb((byte) i));
            if (i != 0x17)
                assertFalse(Kind.isEtb((byte) i));
        }
    }

    @Test
    void happyIsCan() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x18)
                assertTrue(Kind.isCan((byte) i));
            if (i != 0x18)
                assertFalse(Kind.isCan((byte) i));
        }
    }

    @Test
    void happyIsEm() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x19)
                assertTrue(Kind.isEm((byte) i));
            if (i != 0x19)
                assertFalse(Kind.isEm((byte) i));
        }
    }

    @Test
    void happyIsSub() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x1A)
                assertTrue(Kind.isSub((byte) i));
            if (i != 0x1A)
                assertFalse(Kind.isSub((byte) i));
        }
    }

    @Test
    void happyIsEsc() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x1B)
                assertTrue(Kind.isEsc((byte) i));
            if (i != 0x1B)
                assertFalse(Kind.isEsc((byte) i));
        }
    }

    @Test
    void happyIsFs() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x1C)
                assertTrue(Kind.isFs((byte) i));
            if (i != 0x1C)
                assertFalse(Kind.isFs((byte) i));
        }
    }

    @Test
    void happyIsGs() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x1D)
                assertTrue(Kind.isGs((byte) i));
            if (i != 0x1D)
                assertFalse(Kind.isGs((byte) i));
        }
    }

    @Test
    void happyIsRs() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x1E)
                assertTrue(Kind.isRs((byte) i));
            if (i != 0x1E)
                assertFalse(Kind.isRs((byte) i));
        }
    }

    @Test
    void happyIsUs() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x1F)
                assertTrue(Kind.isUs((byte) i));
            if (i != 0x1F)
                assertFalse(Kind.isUs((byte) i));
        }
    }

    // -----------------------------------------

    // -----------------------------------------

    @Test
    void happyIsSpace() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x20)
                assertTrue(Kind.isSpace((byte) i));
            if (i != 0x20)
                assertFalse(Kind.isSpace((byte) i));
        }
    }

    @Test
    void happyIsExclamationMark() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x21)
                assertTrue(Kind.isExclamationMark((byte) i));
            if (i != 0x21)
                assertFalse(Kind.isExclamationMark((byte) i));
        }
    }

    @Test
    void happyIsQuotationMark() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x22)
                assertTrue(Kind.isQuotationMark((byte) i));
            if (i != 0x22)
                assertFalse(Kind.isQuotationMark((byte) i));
        }
    }

    @Test
    void happyIsNumberSign() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x23)
                assertTrue(Kind.isNumberSign((byte) i));
            if (i != 0x23)
                assertFalse(Kind.isNumberSign((byte) i));
        }
    }

    @Test
    void happyIsDollarSign() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x24)
                assertTrue(Kind.isDollarSign((byte) i));
            if (i != 0x24)
                assertFalse(Kind.isDollarSign((byte) i));
        }
    }

    @Test
    void happyIsPercentSign() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x25)
                assertTrue(Kind.isPercentSign((byte) i));
            if (i != 0x25)
                assertFalse(Kind.isPercentSign((byte) i));
        }
    }

    @Test
    void happyIsAmpersand() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x26)
                assertTrue(Kind.isAmpersand((byte) i));
            if (i != 0x26)
                assertFalse(Kind.isAmpersand((byte) i));
        }
    }

    @Test
    void happyIsApostrophe() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x27)
                assertTrue(Kind.isApostrophe((byte) i));
            if (i != 0x27)
                assertFalse(Kind.isApostrophe((byte) i));
        }
    }

    @Test
    void happyIsLeftParenthesis() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x28)
                assertTrue(Kind.isLeftParenthesis((byte) i));
            if (i != 0x28)
                assertFalse(Kind.isLeftParenthesis((byte) i));
        }
    }

    @Test
    void happyIsRightParenthesis() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x29)
                assertTrue(Kind.isRightParenthesis((byte) i));
            if (i != 0x29)
                assertFalse(Kind.isRightParenthesis((byte) i));
        }
    }

    @Test
    void happyIsAsterisk() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x2A)
                assertTrue(Kind.isAsterisk((byte) i));
            if (i != 0x2A)
                assertFalse(Kind.isAsterisk((byte) i));
        }
    }

    @Test
    void happyIsPlus() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x2B)
                assertTrue(Kind.isPlus((byte) i));
            if (i != 0x2B)
                assertFalse(Kind.isPlus((byte) i));
        }
    }

    @Test
    void happyIsComma() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x2C)
                assertTrue(Kind.isComma((byte) i));
            if (i != 0x2C)
                assertFalse(Kind.isComma((byte) i));
        }
    }

    @Test
    void happyIsHyphenMinus() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x2D)
                assertTrue(Kind.isHyphenMinus((byte) i));
            if (i != 0x2D)
                assertFalse(Kind.isHyphenMinus((byte) i));
        }
    }

    @Test
    void happyIsFullStop() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x2E)
                assertTrue(Kind.isFullStop((byte) i));
            if (i != 0x2E)
                assertFalse(Kind.isFullStop((byte) i));
        }
    }

    @Test
    void happyIsSolidus() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x2F)
                assertTrue(Kind.isSolidus((byte) i));
            if (i != 0x2F)
                assertFalse(Kind.isSolidus((byte) i));
        }
    }

    // -----------------------------------------

    @Test
    void happyIsDigitZero() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x30)
                assertTrue(Kind.isDigitZero((byte) i));
            if (i != 0x30)
                assertFalse(Kind.isDigitZero((byte) i));
        }
    }

    @Test
    void happyIsDigitOne() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x31)
                assertTrue(Kind.isDigitOne((byte) i));
            if (i != 0x31)
                assertFalse(Kind.isDigitOne((byte) i));
        }
    }

    @Test
    void happyIsDigitTwo() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x32)
                assertTrue(Kind.isDigitTwo((byte) i));
            if (i != 0x32)
                assertFalse(Kind.isDigitTwo((byte) i));
        }
    }

    @Test
    void happyIsDigitThree() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x33)
                assertTrue(Kind.isDigitThree((byte) i));
            if (i != 0x33)
                assertFalse(Kind.isDigitThree((byte) i));
        }
    }

    @Test
    void happyIsDigitFour() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x34)
                assertTrue(Kind.isDigitFour((byte) i));
            if (i != 0x34)
                assertFalse(Kind.isDigitFour((byte) i));
        }
    }

    @Test
    void happyIsDigitFive() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x35)
                assertTrue(Kind.isDigitFive((byte) i));
            if (i != 0x35)
                assertFalse(Kind.isDigitFive((byte) i));
        }
    }

    @Test
    void happyIsDigitSix() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x36)
                assertTrue(Kind.isDigitSix((byte) i));
            if (i != 0x36)
                assertFalse(Kind.isDigitSix((byte) i));
        }
    }

    @Test
    void happyIsDigitSeven() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x37)
                assertTrue(Kind.isDigitSeven((byte) i));
            if (i != 0x37)
                assertFalse(Kind.isDigitSeven((byte) i));
        }
    }

    @Test
    void happyIsDigitEight() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x38)
                assertTrue(Kind.isDigitEight((byte) i));
            if (i != 0x38)
                assertFalse(Kind.isDigitEight((byte) i));
        }
    }

    @Test
    void happyIsDigitNine() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x39)
                assertTrue(Kind.isDigitNine((byte) i));
            if (i != 0x39)
                assertFalse(Kind.isDigitNine((byte) i));
        }
    }

    @Test
    void happyIsColon() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x3A)
                assertTrue(Kind.isColon((byte) i));
            if (i != 0x3A)
                assertFalse(Kind.isColon((byte) i));
        }
    }

    @Test
    void happyIsSemicolon() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x3B)
                assertTrue(Kind.isSemicolon((byte) i));
            if (i != 0x3B)
                assertFalse(Kind.isSemicolon((byte) i));
        }
    }

    @Test
    void happyIsLessThanSign() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x3C)
                assertTrue(Kind.isLessThanSign((byte) i));
            if (i != 0x3C)
                assertFalse(Kind.isLessThanSign((byte) i));
        }
    }

    @Test
    void happyIsEquals() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x3D)
                assertTrue(Kind.isEquals((byte) i));
            if (i != 0x3D)
                assertFalse(Kind.isEquals((byte) i));
        }
    }

    @Test
    void happyIsGreaterThanSign() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x3E)
                assertTrue(Kind.isGreaterThanSign((byte) i));
            if (i != 0x3E)
                assertFalse(Kind.isGreaterThanSign((byte) i));
        }
    }

    @Test
    void happyIsQuestionMark() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x3F)
                assertTrue(Kind.isQuestionMark((byte) i));
            if (i != 0x3F)
                assertFalse(Kind.isQuestionMark((byte) i));
        }
    }

    // -----------------------------------------

    @Test
    void happyIsCommercialAt() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x40)
                assertTrue(Kind.isCommercialAt((byte) i));
            if (i != 0x40)
                assertFalse(Kind.isCommercialAt((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterA() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x41)
                assertTrue(Kind.isLatinCapitalLetterA((byte) i));
            if (i != 0x41)
                assertFalse(Kind.isLatinCapitalLetterA((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterB() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x42)
                assertTrue(Kind.isLatinCapitalLetterB((byte) i));
            if (i != 0x42)
                assertFalse(Kind.isLatinCapitalLetterB((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterC() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x43)
                assertTrue(Kind.isLatinCapitalLetterC((byte) i));
            if (i != 0x43)
                assertFalse(Kind.isLatinCapitalLetterC((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterD() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x44)
                assertTrue(Kind.isLatinCapitalLetterD((byte) i));
            if (i != 0x44)
                assertFalse(Kind.isLatinCapitalLetterD((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterE() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x45)
                assertTrue(Kind.isLatinCapitalLetterE((byte) i));
            if (i != 0x45)
                assertFalse(Kind.isLatinCapitalLetterE((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterF() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x46)
                assertTrue(Kind.isLatinCapitalLetterF((byte) i));
            if (i != 0x46)
                assertFalse(Kind.isLatinCapitalLetterF((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterG() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x47)
                assertTrue(Kind.isLatinCapitalLetterG((byte) i));
            if (i != 0x47)
                assertFalse(Kind.isLatinCapitalLetterG((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterH() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x48)
                assertTrue(Kind.isLatinCapitalLetterH((byte) i));
            if (i != 0x48)
                assertFalse(Kind.isLatinCapitalLetterH((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterI() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x49)
                assertTrue(Kind.isLatinCapitalLetterI((byte) i));
            if (i != 0x49)
                assertFalse(Kind.isLatinCapitalLetterI((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterJ() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x4A)
                assertTrue(Kind.isLatinCapitalLetterJ((byte) i));
            if (i != 0x4A)
                assertFalse(Kind.isLatinCapitalLetterJ((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterK() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x4B)
                assertTrue(Kind.isLatinCapitalLetterK((byte) i));
            if (i != 0x4B)
                assertFalse(Kind.isLatinCapitalLetterK((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterL() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x4C)
                assertTrue(Kind.isLatinCapitalLetterL((byte) i));
            if (i != 0x4C)
                assertFalse(Kind.isLatinCapitalLetterL((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterM() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x4D)
                assertTrue(Kind.isLatinCapitalLetterM((byte) i));
            if (i != 0x4D)
                assertFalse(Kind.isLatinCapitalLetterM((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterN() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x4E)
                assertTrue(Kind.isLatinCapitalLetterN((byte) i));
            if (i != 0x4E)
                assertFalse(Kind.isLatinCapitalLetterN((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterO() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x4F)
                assertTrue(Kind.isLatinCapitalLetterO((byte) i));
            if (i != 0x4F)
                assertFalse(Kind.isLatinCapitalLetterO((byte) i));
        }
    }

    // -----------------------------------------

    @Test
    void happyIsLatinCapitalLetterP() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x50)
                assertTrue(Kind.isLatinCapitalLetterP((byte) i));
            if (i != 0x50)
                assertFalse(Kind.isLatinCapitalLetterP((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterQ() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x51)
                assertTrue(Kind.isLatinCapitalLetterQ((byte) i));
            if (i != 0x51)
                assertFalse(Kind.isLatinCapitalLetterQ((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterR() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x52)
                assertTrue(Kind.isLatinCapitalLetterR((byte) i));
            if (i != 0x52)
                assertFalse(Kind.isLatinCapitalLetterR((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterS() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x53)
                assertTrue(Kind.isLatinCapitalLetterS((byte) i));
            if (i != 0x53)
                assertFalse(Kind.isLatinCapitalLetterS((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterT() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x54)
                assertTrue(Kind.isLatinCapitalLetterT((byte) i));
            if (i != 0x54)
                assertFalse(Kind.isLatinCapitalLetterT((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterU() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x55)
                assertTrue(Kind.isLatinCapitalLetterU((byte) i));
            if (i != 0x55)
                assertFalse(Kind.isLatinCapitalLetterU((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterV() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x56)
                assertTrue(Kind.isLatinCapitalLetterV((byte) i));
            if (i != 0x56)
                assertFalse(Kind.isLatinCapitalLetterV((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterW() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x57)
                assertTrue(Kind.isLatinCapitalLetterW((byte) i));
            if (i != 0x57)
                assertFalse(Kind.isLatinCapitalLetterW((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterX() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x58)
                assertTrue(Kind.isLatinCapitalLetterX((byte) i));
            if (i != 0x58)
                assertFalse(Kind.isLatinCapitalLetterX((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterY() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x59)
                assertTrue(Kind.isLatinCapitalLetterY((byte) i));
            if (i != 0x59)
                assertFalse(Kind.isLatinCapitalLetterY((byte) i));
        }
    }

    @Test
    void happyIsLatinCapitalLetterZ() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x5A)
                assertTrue(Kind.isLatinCapitalLetterZ((byte) i));
            if (i != 0x5A)
                assertFalse(Kind.isLatinCapitalLetterZ((byte) i));
        }
    }

    @Test
    void happyIsLeftSquareBracket() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x5B)
                assertTrue(Kind.isLeftSquareBracket((byte) i));
            if (i != 0x5B)
                assertFalse(Kind.isLeftSquareBracket((byte) i));
        }
    }

    @Test
    void happyIsReverseSolidus() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x5C)
                assertTrue(Kind.isReverseSolidus((byte) i));
            if (i != 0x5C)
                assertFalse(Kind.isReverseSolidus((byte) i));
        }
    }

    @Test
    void happyIsRightSquareBracket() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x5D)
                assertTrue(Kind.isRightSquareBracket((byte) i));
            if (i != 0x5D)
                assertFalse(Kind.isRightSquareBracket((byte) i));
        }
    }

    @Test
    void happyIsCircumflexAccent() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x5E)
                assertTrue(Kind.isCircumflexAccent((byte) i));
            if (i != 0x5E)
                assertFalse(Kind.isCircumflexAccent((byte) i));
        }
    }

    @Test
    void happyIsLowLine() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x5F)
                assertTrue(Kind.isLowLine((byte) i));
            if (i != 0x5F)
                assertFalse(Kind.isLowLine((byte) i));
        }
    }

    // -----------------------------------------

    @Test
    void happyIsGraveAccent() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x60)
                assertTrue(Kind.isGraveAccent((byte) i));
            if (i != 0x60)
                assertFalse(Kind.isGraveAccent((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterA() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x61)
                assertTrue(Kind.isLatinSmallLetterA((byte) i));
            if (i != 0x61)
                assertFalse(Kind.isLatinSmallLetterA((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterB() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x62)
                assertTrue(Kind.isLatinSmallLetterB((byte) i));
            if (i != 0x62)
                assertFalse(Kind.isLatinSmallLetterB((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterC() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x63)
                assertTrue(Kind.isLatinSmallLetterC((byte) i));
            if (i != 0x63)
                assertFalse(Kind.isLatinSmallLetterC((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterD() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x64)
                assertTrue(Kind.isLatinSmallLetterD((byte) i));
            if (i != 0x64)
                assertFalse(Kind.isLatinSmallLetterD((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterE() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x65)
                assertTrue(Kind.isLatinSmallLetterE((byte) i));
            if (i != 0x65)
                assertFalse(Kind.isLatinSmallLetterE((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterF() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x66)
                assertTrue(Kind.isLatinSmallLetterF((byte) i));
            if (i != 0x66)
                assertFalse(Kind.isLatinSmallLetterF((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterG() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x67)
                assertTrue(Kind.isLatinSmallLetterG((byte) i));
            if (i != 0x67)
                assertFalse(Kind.isLatinSmallLetterG((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterH() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x68)
                assertTrue(Kind.isLatinSmallLetterH((byte) i));
            if (i != 0x68)
                assertFalse(Kind.isLatinSmallLetterH((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterI() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x69)
                assertTrue(Kind.isLatinSmallLetterI((byte) i));
            if (i != 0x69)
                assertFalse(Kind.isLatinSmallLetterI((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterJ() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x6A)
                assertTrue(Kind.isLatinSmallLetterJ((byte) i));
            if (i != 0x6A)
                assertFalse(Kind.isLatinSmallLetterJ((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterK() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x6B)
                assertTrue(Kind.isLatinSmallLetterK((byte) i));
            if (i != 0x6B)
                assertFalse(Kind.isLatinSmallLetterK((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterL() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x6C)
                assertTrue(Kind.isLatinSmallLetterL((byte) i));
            if (i != 0x6C)
                assertFalse(Kind.isLatinSmallLetterL((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterM() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x6D)
                assertTrue(Kind.isLatinSmallLetterM((byte) i));
            if (i != 0x6D)
                assertFalse(Kind.isLatinSmallLetterM((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterN() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x6E)
                assertTrue(Kind.isLatinSmallLetterN((byte) i));
            if (i != 0x6E)
                assertFalse(Kind.isLatinSmallLetterN((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterO() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x6F)
                assertTrue(Kind.isLatinSmallLetterO((byte) i));
            if (i != 0x6F)
                assertFalse(Kind.isLatinSmallLetterO((byte) i));
        }
    }

    // -----------------------------------------

    @Test
    void happyIsLatinSmallLetterP() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x70)
                assertTrue(Kind.isLatinSmallLetterP((byte) i));
            if (i != 0x70)
                assertFalse(Kind.isLatinSmallLetterP((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterQ() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x71)
                assertTrue(Kind.isLatinSmallLetterQ((byte) i));
            if (i != 0x71)
                assertFalse(Kind.isLatinSmallLetterQ((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterR() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x72)
                assertTrue(Kind.isLatinSmallLetterR((byte) i));
            if (i != 0x72)
                assertFalse(Kind.isLatinSmallLetterR((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterS() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x73)
                assertTrue(Kind.isLatinSmallLetterS((byte) i));
            if (i != 0x73)
                assertFalse(Kind.isLatinSmallLetterS((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterT() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x74)
                assertTrue(Kind.isLatinSmallLetterT((byte) i));
            if (i != 0x74)
                assertFalse(Kind.isLatinSmallLetterT((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterU() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x75)
                assertTrue(Kind.isLatinSmallLetterU((byte) i));
            if (i != 0x75)
                assertFalse(Kind.isLatinSmallLetterU((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterV() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x76)
                assertTrue(Kind.isLatinSmallLetterV((byte) i));
            if (i != 0x76)
                assertFalse(Kind.isLatinSmallLetterV((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterW() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x77)
                assertTrue(Kind.isLatinSmallLetterW((byte) i));
            if (i != 0x77)
                assertFalse(Kind.isLatinSmallLetterW((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterX() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x78)
                assertTrue(Kind.isLatinSmallLetterX((byte) i));
            if (i != 0x78)
                assertFalse(Kind.isLatinSmallLetterX((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterY() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x79)
                assertTrue(Kind.isLatinSmallLetterY((byte) i));
            if (i != 0x79)
                assertFalse(Kind.isLatinSmallLetterY((byte) i));
        }
    }

    @Test
    void happyIsLatinSmallLetterZ() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x7A)
                assertTrue(Kind.isLatinSmallLetterZ((byte) i));
            if (i != 0x7A)
                assertFalse(Kind.isLatinSmallLetterZ((byte) i));
        }
    }

    @Test
    void happyIsLeftCurlyBracket() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x7B)
                assertTrue(Kind.isLeftCurlyBracket((byte) i));
            if (i != 0x7B)
                assertFalse(Kind.isLeftCurlyBracket((byte) i));
        }
    }

    @Test
    void happyIsVerticalLine() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x7C)
                assertTrue(Kind.isVerticalLine((byte) i));
            if (i != 0x7C)
                assertFalse(Kind.isVerticalLine((byte) i));
        }
    }

    @Test
    void happyIsRightCurlyBracket() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x7D)
                assertTrue(Kind.isRightCurlyBracket((byte) i));
            if (i != 0x7D)
                assertFalse(Kind.isRightCurlyBracket((byte) i));
        }
    }

    @Test
    void happyIsTilde() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x7E)
                assertTrue(Kind.isTilde((byte) i));
            if (i != 0x7E)
                assertFalse(Kind.isTilde((byte) i));
        }
    }

    @Test
    void happyIsDelete() {
        for (int i = 0; i < 0x80; i++) {
            if (i == 0x7F)
                assertTrue(Kind.isDelete((byte) i));
            if (i != 0x7F)
                assertFalse(Kind.isDelete((byte) i));
        }
    }
}
