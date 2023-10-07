package q9k.buaa.Frontend.AST;

import java.io.IOException;

public class ConstExp implements Syntax{
    private Syntax add_exp;

    public ConstExp(Syntax add_exp) {
        this.add_exp = add_exp;
        handleError();
    }

    @Override
    public void print() throws IOException {
        add_exp.print();
        print_ast_name(ConstExp.class);
    }

    @Override
    public void handleError() {

    }
}
