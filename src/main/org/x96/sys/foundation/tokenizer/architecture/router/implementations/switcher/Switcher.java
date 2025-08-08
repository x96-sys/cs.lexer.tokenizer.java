package org.x96.sys.foundation.tokenizer.architecture.router.implementations.switcher;

import org.x96.sys.foundation.buzz.tokenizer.architecture.router.implementations.switcher.BuzzSwitcherEmpty;
import org.x96.sys.foundation.token.Token;
import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.factory.ReflectiveVisitorFactory;
import org.x96.sys.foundation.tokenizer.architecture.router.implementations.oracle.Oracle;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;

public class Switcher extends Oracle {

    public Switcher() {
        clean();
    }

    @Override
    public Class<? extends Visitor> visitorTo(int hex) {
        if (this.visitors == null || this.visitors.length == 0) throw new BuzzSwitcherEmpty();
        Class<? extends Visitor> v = dispatcher().getTable().get(hex);
        clean();
        return v;
    }

    @Override
    public Token[] stream(Tokenizer tokenizer) {
        return ReflectiveVisitorFactory.happens(visitorTo(tokenizer.look()), tokenizer).safeVisit();
    }
}
