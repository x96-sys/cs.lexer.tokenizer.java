package org.x96.sys.foundation.tokenizer.architecture.router.implementations.serial;

import org.x96.sys.foundation.buzz.Buzz;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.implementations.serial.BuzzCantSerialize;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.implementations.serial.BuzzUnexpectedTokenForVisitor;
import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.factory.ReflectiveVisitorFactory;
import org.x96.sys.foundation.tokenizer.architecture.router.base.Router;
import org.x96.sys.foundation.tokenizer.architecture.router.implementations.serial.architecture.Quantifier;
import org.x96.sys.foundation.tokenizer.architecture.router.implementations.serial.architecture.Step;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;
import org.x96.sys.foundation.tokenizer.token.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Serial extends Router {
    public final List<Step> queued;

    @Override
    public Class<? extends Visitor> visitorTo(int hex) {
        throw new Buzz("serial n implementa visitorTo");
    }

    public Serial() {
        this.queued = new ArrayList<>();
    }

    public Step visitorInLine() {
        return this.queued.getFirst();
    }

    @Override
    public void know(Class<? extends Visitor> visitorClass) {
        one(visitorClass);
    }

    public void one(Class<? extends Visitor> visitorClass) {
        this.queued.add(new Step(visitorClass, Quantifier.JUST_ONE));
    }

    public void zeroOrOne(Class<? extends Visitor> visitorClass) {
        this.queued.add(new Step(visitorClass, Quantifier.ZERO_OR_ONE));
    }

    public void zeroOrMore(Class<? extends Visitor> visitorClass) {
        this.queued.add(new Step(visitorClass, Quantifier.ZERO_OR_MORE));
    }

    public void oneOrMore(Class<? extends Visitor> visitorClass) {
        this.queued.add(new Step(visitorClass, Quantifier.ONE_OR_MORE));
    }

    @Override
    public Token[] stream(Tokenizer tokenizer) {
        List<Token> tokens = new ArrayList<>();
        try {
            knock(tokenizer, tokens);
            afterStream(tokenizer);
        } catch (BuzzUnexpectedTokenForVisitor e) {
            throw new BuzzCantSerialize(tokenizer, visitorInLine(), e);
        }

        return tokens.toArray(new Token[0]);
    }

    private void afterStream(Tokenizer t) {
        if (!t.ready()) { // completou stream
            if (!this.queued.isEmpty() && visitorInLine().count > 0) { // ultimo visitante; e com registro de visita
                this.queued.removeFirst();
                afterStream(t);
            }
            if (!this.queued.isEmpty()
                    && (visitorInLine().quantifier == Quantifier.ZERO_OR_MORE
                    || visitorInLine().quantifier == Quantifier.ZERO_OR_ONE)) { // ultimo visitante podia ser zero
                this.queued.removeFirst();
                afterStream(t);
            }
            if (!this.queued.isEmpty()) {
                throw new BuzzCantSerialize(visitorInLine());
            }
        }
    }

    private void knock(Tokenizer t, List<Token> r) {
        if (!this.queued.isEmpty()) {
            if (t.ready()) {
                Class<? extends Visitor> v = visitorInLine().visitor;
                boolean allowed = false;
                if (ReflectiveVisitorFactory.happens(v, t).allowed()) {
                    r.addAll(Arrays.asList(ReflectiveVisitorFactory.happens(v, t).visit()));
                    allowed = true;
                }
                afterVisit(allowed, t);
                knock(t, r);
            }
        }
    }

    private void afterVisit(boolean allowed, Tokenizer t) {
        switch (visitorInLine().quantifier) {
            case ZERO_OR_ONE -> {
                this.queued.removeFirst();
            }
            case JUST_ONE -> {
                if (!allowed) {
                    throw new BuzzUnexpectedTokenForVisitor(visitorInLine());
                } else {
                    this.queued.removeFirst();
                }
            }
            case ONE_OR_MORE -> {
                if (allowed) {
                    visitorInLine().count += 1;
                } else {
                    if (visitorInLine().count == 0) {
                        throw new BuzzUnexpectedTokenForVisitor(visitorInLine());
                    } else {
                        this.queued.removeFirst();
                    }
                }
            }
            case ZERO_OR_MORE -> {
                if (allowed) {
                    visitorInLine().count += 1;
                } else {
                    this.queued.removeFirst();
                }
            }
        }
    }

    @Override
    public void clean() {
        this.queued.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
                "- Current Serial [%s]",
                this.queued.size()
        ));
        for (int i = 0; i < this.queued.size(); i++) {
            sb.append(String.format(
                    "%n  %s => [%s]",
                    i,
                    this.queued.get(i).toString()
            ));
        }
        return sb.toString().strip();
    }
}
