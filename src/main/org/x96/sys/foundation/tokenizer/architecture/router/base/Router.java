package org.x96.sys.foundation.tokenizer.architecture.router.base;

import org.x96.sys.foundation.tokenizer.architecture.router.architecture.Dispatcher;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;

import java.util.Arrays;

public abstract class Router implements Routing {
    public Class<? extends Visitor>[] visitors;
    public Dispatcher dispatcher;
    public boolean analyzed = false;

    public Dispatcher dispatcher() {
        if (!analyzed) analysis();
        return this.dispatcher;
    }

    public void know(Class<? extends Visitor> visitorClass) {
        Class<? extends Visitor>[] arrayVisitors = Arrays.copyOf(visitors, visitors.length + 1);
        arrayVisitors[visitors.length] = visitorClass;
        this.visitors = arrayVisitors;
        this.analyzed = false;
    }

    @Override
    public Class<? extends Visitor> visitorTo(int hex) {
        throw new RuntimeException("deve ser implementado na classe filha");
    }

    @Override
    public void analysis() {
        if (!this.analyzed) {
            this.dispatcher = new Dispatcher(this);
            this.analyzed = true;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clean() {
        this.visitors = new Class[] {};
    }
}
