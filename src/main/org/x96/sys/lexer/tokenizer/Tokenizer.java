package org.x96.sys.lexer.tokenizer;

import org.x96.sys.io.ByteStream;
import org.x96.sys.lexer.token.Kind;
import org.x96.sys.lexer.token.Token;
import org.x96.sys.lexer.token.architecture.Lexeme;
import org.x96.sys.lexer.token.architecture.span.Position;
import org.x96.sys.lexer.token.architecture.span.Span;
import org.x96.sys.lexer.tokenizer.architecture.CalcPosition;

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

    public Token tokenize(String overKind) {
        return tokenize().overKind(overKind);
    }

    public Token tokenize() {
        Position start = position();
        byte hex = look();
        Kind kind = kind();
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
        if (line > calc.line_starts.length + 1)
            return ""; // +1 porque a última linha pode não ter \n

        int index = line - 1;
        int start = encontraOndeInicia(index);
        int end = encontraOndeTermina(index);

        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append((char) byteStream.at(i));
        }
        return sb.toString();
    }

    private int encontraOndeInicia(int index) {
        if (index == 0) {
            return 0;
        }
        return calc.line_starts[index - 1] + 1;
    }

    private int encontraOndeTermina(int index) {
        if (index >= calc.line_starts.length) {
            return length();
        } else {
            return calc.line_starts[index];
        }
    }
}
