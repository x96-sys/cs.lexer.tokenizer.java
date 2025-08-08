package org.x96.sys.foundation.buzz.architecture.visitor.core;

import org.x96.sys.foundation.buzz.Buzz;

public class BuzzTokenizerRequired extends Buzz {
    private static final int CODE = 0xC2;

    public BuzzTokenizerRequired() {
        super(CODE, BuzzTokenizerRequired.class.getSimpleName(), "tokenizer can't be null");
    }
}
