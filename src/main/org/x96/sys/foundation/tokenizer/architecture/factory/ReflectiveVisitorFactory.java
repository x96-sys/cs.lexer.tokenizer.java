package org.x96.sys.foundation.tokenizer.architecture.factory;

import org.x96.sys.foundation.buzz.Buzz;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.core.BuzzUnbuildableVisitor;
import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class ReflectiveVisitorFactory {
    private static Constructor<? extends Visitor> build(Class<? extends Visitor> cls) {
        if (cls == null) {
            throw new Buzz("n pode ser null");
        }
        try {
            return cls.getConstructor(Tokenizer.class);
        } catch (NoSuchMethodException e) {
            throw new BuzzUnbuildableVisitor(cls, e);
        }
    }

    public static Visitor happens(Class<? extends Visitor> cls, Tokenizer tokenizer) {
        if (cls == null) {
            throw new Buzz("n pode ser null");
        }
        try {
            return build(cls).newInstance(tokenizer);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BuzzUnbuildableVisitor(cls, e);
        }
    }
}
