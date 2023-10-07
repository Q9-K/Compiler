package q9k.buaa.Frontend.AST;

import java.io.IOException;

public class Exp implements Syntax{
    private Syntax add_exp;


    public Exp(Syntax add_exp) {
        this.add_exp = add_exp;
        handleError();
    }

    @Override
    public void print() throws IOException {
        add_exp.print();
        print_ast_name(Exp.class);
    }

    @Override
    public void handleError() {

    }
}
