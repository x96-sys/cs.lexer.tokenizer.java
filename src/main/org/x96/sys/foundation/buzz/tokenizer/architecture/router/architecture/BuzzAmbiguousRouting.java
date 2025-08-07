package org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture;

import org.x96.sys.foundation.buzz.Buzz;

public class BuzzAmbiguousRouting extends Buzz {
    private static final int CODE = 0xE1;

    public BuzzAmbiguousRouting(String detalhe, BuzzAmbiguousVisitor e) {
        super(format(CODE, BuzzAmbiguousRouting.class.getSimpleName(), detalhe), e);
    }
}
