package org.x96.sys.foundation.tokenizer.architecture.visitor.implementations.terminals;

import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;

public class Terminal extends Visitor {
    public Terminal(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public boolean allowed() {
        return true;
    }
}
