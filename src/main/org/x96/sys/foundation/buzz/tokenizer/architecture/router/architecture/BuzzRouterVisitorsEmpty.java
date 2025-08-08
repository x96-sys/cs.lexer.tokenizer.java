package org.x96.sys.foundation.buzz.tokenizer.architecture.router.architecture;

import org.x96.sys.foundation.buzz.Buzz;

public class BuzzRouterVisitorsEmpty extends Buzz {
    private static final int CODE = 0xE2;

    public BuzzRouterVisitorsEmpty(Buzz e) {
        super(
                CODE,
                BuzzRouterVisitorsEmpty.class.getSimpleName(),
                "Nenhum visitante registrado",
                e);
    }
}
