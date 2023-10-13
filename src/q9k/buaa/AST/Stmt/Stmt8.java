package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;

import java.io.IOException;

public class Stmt8 implements Stmt{
    private Syntax exp;
    private Token semicn_token;

    public Stmt8(Syntax exp, Token semicn_token) {
        this.exp = exp;
        this.semicn_token = semicn_token;
    }

    @Override
    public void print() throws IOException {
        if(exp!=null){
            exp.print();
        }
        semicn_token.print();
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {
        if(exp!=null){
            exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        return exp.getLineNumber();
    }


}
