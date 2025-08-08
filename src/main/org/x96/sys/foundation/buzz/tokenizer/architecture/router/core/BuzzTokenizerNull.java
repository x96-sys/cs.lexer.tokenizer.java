package org.x96.sys.foundation.buzz.tokenizer.architecture.router.core;

import org.x96.sys.foundation.buzz.Buzz;

public class BuzzTokenizerNull extends Buzz {
    public static final int CODE = 0xE5;

    public BuzzTokenizerNull() {
        super(CODE, BuzzTokenizerNull.class.getSimpleName(), "O Tokenizer não pode ser nulo.");
    }
}
