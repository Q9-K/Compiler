package q9k.buaa.Frontend.AST;

public class Cond implements Syntax{
    private Syntax l_or_exp;

    public Cond(Syntax l_or_exp) {
        this.l_or_exp = l_or_exp;
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
