package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.LVal;
import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;

import java.io.IOException;

public class Stmt7 implements Stmt{
    private Syntax l_val;
    private Token assign_token;
    private Syntax exp;
    private Token getint_token;
    private Token lparent_token;
    private Token rparent_token;
    private Token semicn_token;

    public Stmt7(Syntax l_val, Token assign_token, Syntax exp, Token getint_token, Token lparent_token, Token rparent_token, Token semicn_token) {
        this.l_val = l_val;
        this.assign_token = assign_token;
        this.exp = exp;
        this.getint_token = getint_token;
        this.lparent_token = lparent_token;
        this.rparent_token = rparent_token;
        this.semicn_token = semicn_token;
    }

    @Override
    public void print() throws IOException {
        l_val.print();
        assign_token.print();
        if(exp!=null){
            exp.print();
            semicn_token.print();
        }
        else{
            getint_token.print();
            lparent_token.print();
            rparent_token.print();
            semicn_token.print();
        }
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {
        LVal temp = (LVal)l_val;
        temp.visit();
        temp.visitConst();

    }

    @Override
    public int getLineNumber() {
        if(exp!=null){
            return exp.getLineNumber();
        }
        else{
            return rparent_token.getLineNumber();
        }
    }

}
