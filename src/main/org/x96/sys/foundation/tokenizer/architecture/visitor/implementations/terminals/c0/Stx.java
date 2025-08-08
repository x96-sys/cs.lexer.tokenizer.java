package org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c0;

import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;
import org.x96.sys.foundation.tokenizer.token.Kind;

public class Stx extends Visitor {
    public Stx(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public boolean allowed() {
        return Kind.isStx(look());
    }
}
