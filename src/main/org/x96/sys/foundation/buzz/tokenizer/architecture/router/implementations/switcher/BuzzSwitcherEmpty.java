package org.x96.sys.foundation.buzz.tokenizer.architecture.router.implementations.switcher;

import org.x96.sys.foundation.buzz.Buzz;

public class BuzzSwitcherEmpty extends Buzz {
    public static final int CODE = 0x76;

    public BuzzSwitcherEmpty() {
        super(CODE, BuzzSwitcherEmpty.class.getSimpleName(), "?");
    }
}
