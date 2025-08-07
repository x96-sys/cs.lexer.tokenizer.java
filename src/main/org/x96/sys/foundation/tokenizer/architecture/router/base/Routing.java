package org.x96.sys.foundation.tokenizer.architecture.router.base;

import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visiting;
import org.x96.sys.foundation.tokenizer.token.Token;

public interface Routing {

    public Class<? extends Visiting> visitorTo(int hex);

    public Token[] stream(Tokenizer tokenizer);

    public void analysis();

    public void clean();
}
