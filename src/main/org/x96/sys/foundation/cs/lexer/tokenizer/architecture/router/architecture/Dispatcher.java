package org.x96.sys.foundation.cs.lexer.tokenizer.architecture.router.architecture;

import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzAmbiguousRouting;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzAmbiguousVisitor;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzAnalyzerEmpty;
import org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture.BuzzRouterVisitorsEmpty;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.router.base.Router;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.visitor.base.Visitor;

import java.util.Map;

public final class Dispatcher {
    private final Map<Integer, Class<? extends Visitor>> table;

    public Dispatcher(Router dispatcher) {
        try {
            this.table = Analyzer.build(dispatcher);
        } catch (BuzzAnalyzerEmpty e) {
            throw new BuzzRouterVisitorsEmpty(e);
        } catch (BuzzAmbiguousVisitor e) {
            throw new BuzzAmbiguousRouting("Ambiguidade detectada na fase de roteamento", e);
        }
    }

    public Map<Integer, Class<? extends Visitor>> getTable() {
        return this.table;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("- Dispatcher Table [").append(table.size()).append("]\n");
        String indent = " ".repeat(4);
        for (Map.Entry<Integer, Class<? extends Visitor>> entry : table.entrySet()) {
            sb.append(indent);
            sb.append(String.format("[0x%X] => %s%n", entry.getKey(), entry.getValue().getName()));
        }
        return sb.toString().strip();
    }
}
