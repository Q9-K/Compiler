package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

public class MulExp implements Syntax {
    private Syntax unary_exp;
    private Token op_token;
    private Syntax mul_exp;

    public MulExp(Syntax unary_exp, Token op_token, Syntax mul_exp) {
        this.unary_exp = unary_exp;
        this.op_token = op_token;
        this.mul_exp = mul_exp;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
