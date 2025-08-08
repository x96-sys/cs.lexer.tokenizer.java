package org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c6;

import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;
import org.x96.sys.foundation.tokenizer.token.Kind;

public class LatinSmallLetterC extends Visitor {
    public LatinSmallLetterC(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public boolean allowed() {
        return Kind.isLatinSmallLetterC(look());
    }
}
