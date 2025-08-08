package org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals.c3;

import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;
import org.x96.sys.foundation.tokenizer.token.Kind;

public class DigitOne extends Visitor {
    public DigitOne(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public boolean allowed() {
        return Kind.isDigitOne(look());
    }
}
