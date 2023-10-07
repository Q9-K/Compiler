package q9k.buaa.Frontend.AST;

import java.io.IOException;

public class Stmt6 implements Stmt{
    private Syntax block;

    public Stmt6(Syntax block) {
        this.block = block;
        handleError();
    }

    @Override
    public void print() throws IOException {
        block.print();
        print_ast_name(Stmt.class);

    }

    @Override
    public void handleError() {

    }
}
