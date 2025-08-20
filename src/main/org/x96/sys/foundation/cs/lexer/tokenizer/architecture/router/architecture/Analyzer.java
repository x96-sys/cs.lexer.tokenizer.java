package org.x96.sys.foundation.cs.lexer.tokenizer.architecture.router.architecture;

import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzAmbiguousVisitor;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzAnalyzerEmpty;
import org.x96.sys.foundation.cs.lexer.tokenizer.Tokenizer;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.factory.ReflectiveVisitorFactory;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.router.base.Router;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.visitor.base.Visiting;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.visitor.base.Visitor;
import org.x96.sys.foundation.io.ByteStream;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Analyzer {

    public static Map<Integer, Class<? extends Visitor>> build(Router dispatcher) {
        if (dispatcher.visitors == null || dispatcher.visitors.length == 0) {
            throw new BuzzAnalyzerEmpty();
        }

        Map<Integer, Class<? extends Visitor>> table = new java.util.HashMap<>();
        for (int i = 0; i < 0xFF; i++) {
            Tokenizer tokenizer = new Tokenizer(ByteStream.raw(new byte[] {(byte) i}));

            for (Class<? extends Visitor> cls : dispatcher.visitors) {

                Visiting visitor = ReflectiveVisitorFactory.happens(cls, tokenizer);
                if (visitor.allowed()) {
                    if (table.containsKey(i)) {
                        throw new BuzzAmbiguousVisitor(table, cls, i);
                    } else {
                        table.put(i, cls);
                    }
                }
            }
        }
        return table;
    }

    public static Byte[] discovery(Class<? extends Visitor> v, int from, int to) {
        List<Byte> l = new LinkedList<>();
        for (int i = from; i < to; i++) {
            Tokenizer t = new Tokenizer(ByteStream.raw(new byte[] {(byte) i}));
            if (ReflectiveVisitorFactory.happens(v, t).allowed()) {
                l.add((byte) i);
            }
        }
        return l.toArray(Byte[]::new);
    }
}
