package org.x96.sys.foundation.tokenizer.architecture.visitor.base;

import org.x96.sys.foundation.tokenizer.Tokenizer;

public class GhostVisitor extends Visitor {
    public GhostVisitor(Tokenizer t) {
        super(t);
    }

    @Override
    public boolean allowed() {
        return look() != 0x30;
    }
}
