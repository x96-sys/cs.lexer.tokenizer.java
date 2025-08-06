package org.x96.sys.foundation.tokenizer.token.architecture;

public record Lexeme(byte b) {

    @Override
    public String toString() {
        return String.format("0x%X", b);
    }
}
