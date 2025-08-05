package org.x96.sys.foundation;

import org.x96.sys.foundation.tokenizer.token.Kind;

public class CLI {
    public static void main(String[] args) {
        for (String arg : args) {
            for (byte b : arg.getBytes()) {
                System.out.println(Kind.is(b));
            }
        }
    }
}
