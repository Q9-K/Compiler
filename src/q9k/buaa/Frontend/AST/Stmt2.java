package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class Stmt2 implements Stmt {
    private Token for_token;
    private Token lparent_token;
    private Syntax for_stmt1;
    private Token semicn_token1;
    private Syntax cond;
    private Token semicn_token2;
    private Syntax for_stmt2;
    private Token rparent_token;
    private Syntax stmt;

    public Stmt2(Token for_token, Token lparent_token, Syntax for_stmt1, Token semicn_token1, Syntax cond, Token semicn_token2, Syntax for_stmt2, Token rparent_token, Syntax stmt) {
        this.for_token = for_token;
        this.lparent_token = lparent_token;
        this.for_stmt1 = for_stmt1;
        this.semicn_token1 = semicn_token1;
        this.cond = cond;
        this.semicn_token2 = semicn_token2;
        this.for_stmt2 = for_stmt2;
        this.rparent_token = rparent_token;
        this.stmt = stmt;
        handleError();
    }

    @Override
    public void print() throws IOException {
        for_token.print();
        lparent_token.print();
        if(for_stmt1!=null){
            for_stmt1.print();
        }
        semicn_token1.print();
        if(cond!=null){
            cond.print();
        }
        semicn_token2.print();
        if(for_stmt2!=null){
            for_stmt2.print();
        }
        rparent_token.print();
        stmt.print();
        print_ast_name(Stmt.class);
    }

    @Override
    public void handleError() {

    }
}
