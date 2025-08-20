package org.x96.sys.foundation.cs.lexer.tokenizer.architecture.router.implementations.serial.architecture;

public enum Quantifier {
    JUST_ONE, // default
    ZERO_OR_ONE, // ?
    ZERO_OR_MORE, // *
    ONE_OR_MORE, // +
    REJECT // !
}
