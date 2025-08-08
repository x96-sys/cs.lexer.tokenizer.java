package org.x96.sys.foundation.tokenizer.architecture.router.architecture;

import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzAmbiguousVisitor;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzAnalyzerEmpty;
import org.x96.sys.foundation.io.ByteStream;
import org.x96.sys.foundation.tokenizer.Tokenizer;
import org.x96.sys.foundation.tokenizer.architecture.factory.ReflectiveVisitorFactory;
import org.x96.sys.foundation.tokenizer.architecture.router.base.Router;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visiting;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visitor;

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
}
