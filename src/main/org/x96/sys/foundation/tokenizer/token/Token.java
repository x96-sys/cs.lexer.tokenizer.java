package org.x96.sys.foundation.tokenizer.token;

import org.x96.sys.foundation.tokenizer.token.architecture.Lexeme;
import org.x96.sys.foundation.tokenizer.token.architecture.span.Span;

public record Token(Kind kind, Lexeme lexeme, Span span) {

    @Override
    public String toString() {
        return String.format(
                "Token { Kind[%s] Lexeme[%s] Span[%s] }",
                kind.toString(), lexeme.toString(), span.toString());
    }
}
