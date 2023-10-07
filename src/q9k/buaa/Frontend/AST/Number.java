package q9k.buaa.Frontend.AST;

import java.io.IOException;

public class Number implements Syntax{

    private Syntax int_const;

    public Number(Syntax int_const) {
        this.int_const = int_const;
        handleError();
    }

    @Override
    public void print() throws IOException {
        int_const.print();
        print_ast_name(Number.class);
    }

    @Override
    public void handleError() {

    }
}
