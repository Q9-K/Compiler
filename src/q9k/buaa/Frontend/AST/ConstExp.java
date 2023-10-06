package q9k.buaa.Frontend.AST;

public class ConstExp implements Syntax{
    private Syntax add_exp;

    public ConstExp(Syntax add_exp) {
        this.add_exp = add_exp;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
