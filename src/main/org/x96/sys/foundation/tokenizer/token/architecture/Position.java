package org.x96.sys.foundation.tokenizer.token.architecture;

public record Position(int line, int column, int offset) {

    @Override
    public String toString() {
        return "{" + line + ":" + column + " " + offset + "}";
    }
}
