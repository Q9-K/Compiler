package q9k.buaa.AST;

import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class UnaryOp implements Syntax {
    private Token op_token;

    public UnaryOp(Token op_token) {
        this.op_token = op_token;
    }

    @Override
    public void print() throws IOException {
        op_token.print();
        printAstName(UnaryOp.class);
    }

    @Override
    public void visit() {
    }

    @Override
    public int getLineNumber() {
        return op_token.getLineNumber();
    }

}
