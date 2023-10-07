package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class UnaryOp implements Syntax{
    private Token op_token;

    public UnaryOp(Token op_token) {
        this.op_token = op_token;
        handleError();
    }

    @Override
    public void print() throws IOException {
        op_token.print();
        print_ast_name(UnaryOp.class);
    }

    @Override
    public void handleError() {

    }
}
