package org.x96.sys.foundation.tokenizer;

import org.x96.sys.foundation.io.ByteStream;
import org.x96.sys.foundation.tokenizer.token.Kind;
import org.x96.sys.foundation.tokenizer.token.Token;
import org.x96.sys.foundation.tokenizer.token.architecture.CalcPosition;
import org.x96.sys.foundation.tokenizer.token.architecture.Lexeme;
import org.x96.sys.foundation.tokenizer.token.architecture.span.Position;
import org.x96.sys.foundation.tokenizer.token.architecture.span.Span;

public class Tokenizer {
    private final ByteStream byteStream;
    public final CalcPosition calc;
    private int cursor;

    public Tokenizer(ByteStream byteStream) {
        this.byteStream = byteStream;
        this.cursor = 0;
        this.calc = new CalcPosition(byteStream);
    }

    public int length() {
        return byteStream.length();
    }

    public boolean ready() {
        return cursor < byteStream.length();
    }

    public int pointer() {
        return this.cursor;
    }

    public Position position() {
        return calc.getPosition(this.cursor);
    }

    public byte look() {
        return byteStream.at(pointer());
    }

    public Kind kind() {
        return Kind.is(look());
    }

    public Token tokenize() {
        return tokenize(kind());
    }

    public Token tokenize(Kind kind) {
        Position start = position();
        byte hex = look();
        advance();
        Position end = calc.getPosition(this.cursor);
        Span span = new Span(start, end);
        Lexeme lexeme = new Lexeme(hex);
        return new Token(kind, lexeme, span);
    }

    public void advance() {
        this.cursor += 1;
    }

    public String getLineByNumber(int line) {
        if (line <= 0) return "";
        if (line > calc.line_starts.length) return "";
        StringBuilder sb = new StringBuilder();
        int offset_start = line == 1 ? 0 : calc.line_starts[line - 2] + 1;
        int offset_end = calc.line_starts[line - 1];
        for (int i = offset_start; i < offset_end; i++) {
            int v = byteStream.at(i);
            if (v != 0x2 && v != 0x3) {
                sb.append((char) byteStream.at(i));
            }
        }
        return sb.toString();
    }
}
