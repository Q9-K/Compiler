package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

public class LOrExp implements Syntax{
    private Syntax l_and_exp;
    private Token or_token;
    private Syntax l_or_exp;

    public LOrExp(Syntax l_and_exp, Token or_token, Syntax l_or_exp) {
        this.l_and_exp = l_and_exp;
        this.or_token = or_token;
        this.l_or_exp = l_or_exp;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
