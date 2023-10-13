package q9k.buaa.AST;

import java.io.IOException;

public class ConstExp implements Syntax {
    private Syntax add_exp;

    public ConstExp(Syntax add_exp) {
        this.add_exp = add_exp;
    }

    @Override
    public void print() throws IOException {
        add_exp.print();
        printAstName(ConstExp.class);
    }

    @Override
    public void visit() {
        add_exp.visit();
    }


    @Override
    public int getLineNumber() {
        return add_exp.getLineNumber();
    }
}
