package org.x96.sys.foundation.tokenizer;

import org.x96.sys.foundation.io.ByteStream;

public class Tokenizer {
    private final ByteStream byteStream;

    public Tokenizer(ByteStream byteStream) {
        this.byteStream = byteStream;
    }

    public int length() {
        return this.byteStream.length();
    }
}
