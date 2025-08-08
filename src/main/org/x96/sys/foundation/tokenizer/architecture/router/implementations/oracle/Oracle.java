package org.x96.sys.foundation.tokenizer.architecture.router.implementations.oracle;

import org.x96.sys.foundation.token.Token;
import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.factory.ReflectiveVisitorFactory;
import org.x96.sys.foundation.tokenizer.architecture.router.base.Router;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Oracle extends Router {

    public Oracle() {
        clean();
    }

    @Override
    public Class<? extends Visitor> visitorTo(int hex) {
        return dispatcher().getTable().get(hex);
    }

    @Override
    public Token[] stream(Tokenizer tokenizer) {
        List<Token> tokens = new ArrayList<>();
        resolve(tokenizer, tokens);
        return tokens.toArray(new Token[0]);
    }

    private void resolve(Tokenizer t, List<Token> r) {
        if (t.ready()) {
            Class<? extends Visitor> v = visitorTo(t.look());
            if (v == null) return;
            r.addAll(Arrays.asList(ReflectiveVisitorFactory.happens(v, t).visit()));
            if (t.ready()) resolve(t, r);
        }
    }
}
