package org.x96.sys.foundation.tokenizer.architecture.visitor.base;

import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.token.Kind;
import org.x96.sys.foundation.tokenizer.token.Token;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class Visitor implements Visiting {
    public final Tokenizer tokenizer;
    public final LinkedList<Token> tokens;

    public Visitor(Tokenizer tokenizer) {
        if (tokenizer == null) {
            throw new RuntimeException("BuzzTokenizerRequired");
        }
        this.tokenizer = tokenizer;
        this.tokens = new LinkedList<>();
    }

    @Override
    public Token[] visit() {
        rec(overkind());
        return stream();
    }

    public Token[] safeVisit() {
        if (!allowed()) {
            throw new RuntimeException("BuzzVisitorMismatch");
        } else {
            return visit();
        }
    }

    public Kind overkind() {
        return kind();
    }

    public byte look() {
        return tokenizer.look();
    }

    public Kind kind() {
        return tokenizer.kind();
    }

    public void rec() {
        rec(overkind());
    }

    public void rec(Kind kind) {
        tokens.add(tokenizer.tokenize(kind));
    }

    public void push(Token[] tokenArray) {
        tokens.addAll(Arrays.asList(tokenArray));
    }

    public Token[] stream() {
        return tokens.toArray(Token[]::new);
    }
}
