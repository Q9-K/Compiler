package q9k.buaa.AST;

import java.io.IOException;

public class Number implements Syntax {

    private Syntax int_const;

    public Number(Syntax int_const) {
        this.int_const = int_const;
    }

    @Override
    public void print() throws IOException {
        int_const.print();
        printAstName(Number.class);
    }

    @Override
    public void visit() {

    }

    @Override
    public int getLineNumber() {
        return int_const.getLineNumber();
    }

}
