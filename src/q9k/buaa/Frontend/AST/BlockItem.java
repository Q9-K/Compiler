package q9k.buaa.Frontend.AST;

public class BlockItem implements Syntax{
    private Syntax decl;
    private Syntax stmt;

    public BlockItem(Syntax decl, Syntax stmt) {
        this.decl = decl;
        this.stmt = stmt;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
