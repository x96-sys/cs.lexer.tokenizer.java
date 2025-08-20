package org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture;

import org.x96.sys.foundation.buzz.Buzz;
import org.x96.sys.foundation.cs.lexer.tokenizer.architecture.visitor.base.Visitor;

import java.util.*;

public class BuzzAmbiguousVisitor extends Buzz {
    private static final int CODE = 0xA2;

    public BuzzAmbiguousVisitor(
            Map<Integer, Class<? extends Visitor>> table, Class<? extends Visitor> cls, int i) {
        super(CODE, BuzzAmbiguousVisitor.class.getSimpleName(), explain(table, cls, i).strip());
    }

    private static String explain(
            Map<Integer, Class<? extends Visitor>> table, Class<? extends Visitor> cls, int i) {
        StringBuilder sb = new StringBuilder();

        sb.append(
                "["
                        + cls.getSimpleName()
                        + "] tenta registrar ["
                        + hex(i)
                        + "] ["
                        + (char) i
                        + "] que já é respondido por ["
                        + table.get(i).getSimpleName()
                        + "]\n");
        String indent = " ".repeat(3);
        sb.append(indent);
        sb.append("> {current table} [" + table.size() + "]\n");

        // Agrupar por visitante
        Map<Class<?>, List<Integer>> agrupado = new LinkedHashMap<>();
        for (var entry : table.entrySet()) {
            agrupado.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
        }

        Iterator<Map.Entry<Class<?>, List<Integer>>> it = agrupado.entrySet().iterator();
        indent = indent + " ".repeat(4);
        while (it.hasNext()) {
            var entry = it.next();
            Class<?> tipo = entry.getKey();
            List<Integer> indices = entry.getValue();
            indices.sort(Comparator.naturalOrder());

            String grupos = agrupar(indices);
            sb.append(indent);
            sb.append("> [").append(tipo.getSimpleName()).append("] #").append(grupos);

            if (it.hasNext()) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private static String agrupar(List<Integer> numeros) {
        StringBuilder sb = new StringBuilder();
        int inicio = -1, fim = -1;

        for (int n : numeros) {
            if (inicio == -1) {
                inicio = fim = n;
            } else if (n == fim + 1) {
                fim = n;
            } else {
                appendRange(sb, inicio, fim);
                inicio = fim = n;
            }
        }
        appendRange(sb, inicio, fim);
        return sb.toString();
    }

    private static void appendRange(StringBuilder sb, int inicio, int fim) {
        if (sb.length() > 0) sb.append(", ");
        if (inicio == fim) {
            sb.append("[").append(hex(inicio)).append("]");
        } else {
            sb.append("[").append(hex(inicio)).append("–").append(hex(fim)).append("]");
        }
    }
}
