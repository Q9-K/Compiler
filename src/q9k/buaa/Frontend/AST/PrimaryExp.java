package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

public class PrimaryExp implements Syntax{

    private Token lparent;
    private Syntax exp;
    private Token rparent;
    private Syntax l_val;
    private Syntax number;

    public PrimaryExp(Token lparent, Syntax exp, Token rparent, Syntax l_val, Syntax number) {
        this.lparent = lparent;
        this.exp = exp;
        this.rparent = rparent;
        this.l_val = l_val;
        this.number = number;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
