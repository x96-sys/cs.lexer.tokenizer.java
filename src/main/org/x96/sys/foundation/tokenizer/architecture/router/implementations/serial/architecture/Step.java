package org.x96.sys.foundation.tokenizer.architecture.router.implementations.serial.architecture;

import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;

public class Step {
    public final Class<? extends Visitor> visitor;
    public final Quantifier quantifier;
    public int count;

    public Step(Class<? extends Visitor> visitor, Quantifier quantifier) {
        this.visitor = visitor;
        this.quantifier = quantifier;
        this.count = 0;
    }

    @Override
    public String toString() {
        return String.format(
                "%s; %s",
                quantifier, visitor.getSimpleName()
        );
    }
}
