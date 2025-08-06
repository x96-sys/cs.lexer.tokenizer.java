package org.x96.sys.foundation.tokenizer;

import org.junit.jupiter.api.Test;
import org.x96.sys.foundation.io.ByteStream;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    void happy() {
        Tokenizer tokenizer = new Tokenizer(ByteStream.raw("abxy".getBytes()));
        assertEquals(4, tokenizer.length());
    }
}