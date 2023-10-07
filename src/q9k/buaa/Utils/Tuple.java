package q9k.buaa.Utils;

import q9k.buaa.Frontend.AST.Syntax;
import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class Tuple<A, B> {
    private final A first;
    private final B second;

    public Tuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public void print() throws IOException {
        ((Token)first).print();
        ((Syntax)second).print();
    }
}
