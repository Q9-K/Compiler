package q9k.buaa.Frontend.AST;

import q9k.buaa.INIT.Output;

import java.io.IOException;

public class Cond implements Syntax{
    private Syntax l_or_exp;

    public Cond(Syntax l_or_exp) {
        this.l_or_exp = l_or_exp;
    }

    @Override
    public void print() throws IOException {
        l_or_exp.print();
        print_ast_name(Cond.class);
    }

    @Override
    public void handleError() {

    }
}
