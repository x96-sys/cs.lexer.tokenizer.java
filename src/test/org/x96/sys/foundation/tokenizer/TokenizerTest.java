package org.x96.sys.foundation.tokenizer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.x96.sys.foundation.io.ByteStream;

class TokenizerTest {

    @Test
    void happy() {
        Tokenizer tokenizer = new Tokenizer(ByteStream.raw("abxy".getBytes()));
        assertEquals(4, tokenizer.length());
    }
}
