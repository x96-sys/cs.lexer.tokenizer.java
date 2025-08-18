package org.x96.sys.foundation.tokenizer.architecture.router.implementations.serial;

import org.x96.sys.foundation.buzz.Buzz;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.implementations.serial.BuzzCantSerialize;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.implementations.serial.BuzzUnexpectedTokenForVisitor;
import org.x96.sys.foundation.token.Token;
import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.factory.ReflectiveVisitorFactory;
import org.x96.sys.foundation.tokenizer.architecture.router.base.Router;
import org.x96.sys.foundation.tokenizer.architecture.router.implementations.serial.architecture.Quantifier;
import org.x96.sys.foundation.tokenizer.architecture.router.implementations.serial.architecture.Step;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Serial extends Router {
    public final List<Step> queued;
    public final Quantifier quantifier;
    public int current;

    @Override
    public Class<? extends Visitor> visitorTo(int hex) {
        throw new Buzz("serial n implementa visitorTo");
    }

    public Serial() {
        this.queued = new ArrayList<>();
        this.quantifier = Quantifier.JUST_ONE;
        this.current = 0;
    }

    public Serial(Quantifier quantifier) {
        this.queued = new ArrayList<>();
        this.quantifier = quantifier;
        this.current = 0;
    }

    public Step visitorInLine() {
        return this.queued.get(this.current);
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

    public void reject(Class<? extends Visitor> visitorClass) {
        this.queued.add(new Step(visitorClass, Quantifier.REJECT));
    }

    private void cycle(Tokenizer t, List<Token> l) {
        try {
            knock(t, l);
            afterStream(t);
            reset();
        } catch (BuzzUnexpectedTokenForVisitor e) {
            throw new BuzzCantSerialize(t, visitorInLine(), e);
        }
    }

    @Override
    public Token[] stream(Tokenizer tokenizer) {
        List<Token> tokens = new ArrayList<>();
        cycle(tokenizer, tokens);
        if (this.quantifier == Quantifier.ONE_OR_MORE) {
            while (tokenizer.ready()
                    && ((!visitorIsRejected() && initNextVisitor(tokenizer).allowed())
                            || (visitorIsRejected() && initNextVisitor(tokenizer).denied()))) {
                cycle(tokenizer, tokens);
            }
        }
        return tokens.toArray(Token[]::new);
    }

    public void dequeue() {
        this.current++;
    }

    public boolean hasNext() {
        return this.current < this.queued.size();
    }

    private void afterStream(Tokenizer t) {
        if (!t.ready()) { // completou stream
            if (hasNext()
                    && visitorInLine().count > 0) { // ultimo visitante; e com registro de visita
                dequeue();
                afterStream(t);
            }
            if (hasNext()
                    && (visitorInLine().quantifier == Quantifier.ZERO_OR_MORE
                            || visitorInLine().quantifier
                                    == Quantifier.ZERO_OR_ONE)) { // ultimo visitante podia ser
                // zero
                dequeue();
                afterStream(t);
            }
            if (hasNext()) {
                throw new BuzzCantSerialize(visitorInLine());
            }
        }
    }

    private Visitor initNextVisitor(Tokenizer t) {
        Class<? extends Visitor> v = visitorInLine().visitor;
        return ReflectiveVisitorFactory.happens(v, t);
    }

    private boolean visitorIsRejected() {
        return visitorInLine().quantifier.equals(Quantifier.REJECT);
    }

    private void knock(Tokenizer t, List<Token> r) {
        if (hasNext()) {
            if (t.ready()) {
                boolean allowed = false;
                Visitor visitor = initNextVisitor(t);
                if (visitor.allowed()) {
                    allowed = true;
                    if (!visitorIsRejected()) {
                        r.addAll(Arrays.asList(visitor.visit()));
                    }
                }
                afterVisit(allowed, t);
                knock(t, r);
            }
        }
    }

    private void afterVisit(boolean allowed, Tokenizer t) {
        switch (visitorInLine().quantifier) {
            case ZERO_OR_ONE -> {
                dequeue();
            }
            case JUST_ONE -> {
                if (!allowed) {
                    throw new BuzzUnexpectedTokenForVisitor(visitorInLine());
                } else {
                    dequeue();
                }
            }
            case ONE_OR_MORE -> {
                if (allowed) {
                    visitorInLine().count += 1;
                } else {
                    if (visitorInLine().count == 0) {
                        throw new BuzzUnexpectedTokenForVisitor(visitorInLine());
                    } else {
                        dequeue();
                    }
                }
            }
            case ZERO_OR_MORE -> {
                if (allowed) {
                    visitorInLine().count += 1;
                } else {
                    dequeue();
                }
            }
            case REJECT -> {
                if (allowed) {
                    throw new RuntimeException("rejected token has show up");
                } else {
                    dequeue();
                }
            }
        }
    }

    @Override
    public void clean() {
        this.queued.clear();
        this.current = 0;
    }

    public void reset() {
        for (Step step : this.queued) {
            step.count = 0;
        }
        this.current = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("- Current Serial [%s]", this.queued.size()));
        for (int i = 0; i < this.queued.size(); i++) {
            sb.append(String.format("%n  %s => [%s]", i, this.queued.get(i).toString()));
        }
        return sb.toString().strip();
    }
}
