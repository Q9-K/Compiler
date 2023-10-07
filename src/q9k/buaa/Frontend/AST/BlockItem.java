package q9k.buaa.Frontend.AST;

import java.io.IOException;

public class BlockItem implements Syntax {
    private Syntax decl;
    private Syntax stmt;

    public BlockItem(Syntax decl, Syntax stmt) {
        this.decl = decl;
        this.stmt = stmt;
        handleError();
    }

    @Override
    public void print() throws IOException {
        if (decl != null) {
            decl.print();
        } else {
            stmt.print();
        }
    }

    @Override
    public void handleError() {

    }
}
