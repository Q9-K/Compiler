package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class Stmt8 implements Stmt{
    private Syntax exp;
    private Token semicn_token;

    public Stmt8(Syntax exp, Token semicn_token) {
        this.exp = exp;
        this.semicn_token = semicn_token;
        handleError();
    }

    @Override
    public void print() throws IOException {
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
