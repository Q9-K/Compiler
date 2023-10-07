package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class Stmt4 implements Stmt{

    Token return_token;
    Syntax exp;
    Token semicn_token;

    public Stmt4(Token return_token, Syntax exp, Token semicn_token) {
        this.return_token = return_token;
        this.exp = exp;
        this.semicn_token = semicn_token;
        handleError();
    }

    @Override
    public void print() throws IOException {
        return_token.print();
        if(exp!=null){
            exp.print();
        }
        semicn_token.print();
        print_ast_name(Stmt.class);

    }

    @Override
    public void handleError() {

    }
}
