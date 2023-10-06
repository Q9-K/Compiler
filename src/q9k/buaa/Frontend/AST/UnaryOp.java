package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

public class UnaryOp implements Syntax{
    private Token op_token;

    public UnaryOp(Token op_token) {
        this.op_token = op_token;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
