package q9k.buaa.Utils;

import q9k.buaa.Frontend.AST.Syntax;
import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class Triple<A, B, C> {
    private final A first;
    private final B second;
    private final C third;

    public Triple(A first, B second,C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    public C getThird(){
        return third;
    }

    public void print() throws IOException {
        ((Token)first).print();
        ((Syntax)second).print();
        ((Token)third).print();
    }
}
