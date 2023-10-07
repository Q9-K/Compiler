package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class Stmt3 implements Stmt{
    private Token action_token;
    private Token semicn_token;

    public Stmt3(Token action_token, Token semicn_token) {
        this.action_token = action_token;
        this.semicn_token = semicn_token;
        handleError();
    }

    @Override
    public void print() throws IOException {
        action_token.print();
        semicn_token.print();
        print_ast_name(Stmt.class);
    }

    @Override
    public void handleError() {

    }
}
