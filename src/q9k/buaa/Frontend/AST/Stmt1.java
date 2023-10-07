package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class Stmt1 implements Stmt{
    private Token if_token;
    private Token lparent_token;
    private Syntax cond;
    private Token rparent_token;
    private Syntax stmt1;
    private Token else_token;
    private Syntax stmt2;

    public Stmt1(Token if_token, Token lparent_token, Syntax cond, Token rparent_token, Syntax stmt1, Token else_token, Syntax stmt2) {
        this.if_token = if_token;
        this.lparent_token = lparent_token;
        this.cond = cond;
        this.rparent_token = rparent_token;
        this.stmt1 = stmt1;
        this.else_token = else_token;
        this.stmt2 = stmt2;
        handleError();
    }

    @Override
    public void print() throws IOException {
        if_token.print();
        lparent_token.print();
        cond.print();
        rparent_token.print();
        stmt1.print();
        if(else_token!=null){
            else_token.print();
            stmt2.print();
        }
        print_ast_name(Stmt.class);
    }

    @Override
    public void handleError() {

    }
}
