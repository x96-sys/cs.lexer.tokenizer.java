package org.x96.sys.foundation.buzz.tokenizer.architecture.router.core;

import org.x96.sys.foundation.buzz.Buzz;
import org.x96.sys.foundation.tokenizer.architecture.visitor.base.Visiting;

public class BuzzUnbuildableVisitor extends Buzz {
    private static final int CODE = 0xC1;

    // public HexVisitor(AbstractRouter router, Tokenizer tokenizer) {
    // super(router, tokenizer);
    // }
    public BuzzUnbuildableVisitor(Class<? extends Visiting> cls, Throwable e) {
        super(CODE, BuzzUnbuildableVisitor.class.getSimpleName(), explain(cls), e);
    }

    private static String explain(Class<? extends Visiting> cls) {
        StringBuilder sb = new StringBuilder();
        String msg =
                "Falha ao instanciar visitante '"
                        + cls.getSimpleName()
                        + "' \n"
                        + "[1] Verifique se o construtor "
                        + cls.getSimpleName()
                        + "(VisitorDispatcher, Tokenizer) está definido e acessível.\n"
                        + "[2] Verifique o Tokenizer\n"
                        + "[3] Verifique o VisitorDispatcher";
        sb.append(msg);
        sb.append(msg);

        return sb.toString();
    }
}
