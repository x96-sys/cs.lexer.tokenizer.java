package org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c7;

import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;
import org.x96.sys.foundation.tokenizer.token.Kind;

public class LatinSmallLetterS extends Visitor {

    public LatinSmallLetterS(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public boolean allowed() {
        return Kind.isLatinSmallLetterS(look());
    }
}
