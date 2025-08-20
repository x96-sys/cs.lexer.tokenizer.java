package org.x96.sys.foundation.cs.lexer.tokenizer.architecture.router.base;

import org.x96.sys.foundation.cs.lexer.token.Token;
import org.x96.sys.foundation.cs.lexer.tokenizer.Tokenizer;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.visitor.base.Visiting;

public interface Routing {

    public Class<? extends Visiting> visitorTo(int hex);

    public Token[] stream(Tokenizer tokenizer);

    public void analysis();

    public void clean();
}
