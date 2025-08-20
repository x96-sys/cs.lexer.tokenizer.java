package org.x96.sys.foundation.tokenizer.architecture.visitor.base;

import org.x96.sys.foundation.cs.lexer.tokenizer.Tokenizer;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.visitor.base.Visitor;

public class GhostVisitor extends Visitor {
    public GhostVisitor(Tokenizer t) {
        super(t);
    }

    @Override
    public boolean allowed() {
        return look() != 0x30;
    }
}
