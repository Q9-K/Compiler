package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

public class IntConst implements Syntax{

    private Token intcon_token;

    public IntConst(Token intcon_token) {
        this.intcon_token = intcon_token;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
