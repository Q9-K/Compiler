package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class EqExp implements Syntax,Type {
    private Syntax rel_exp;
    private Token op_token ;
    private Syntax eq_exp ;

    public EqExp(Syntax rel_exp, Token op_token, Syntax eq_exp) {
        this.rel_exp = rel_exp;
        this.op_token = op_token;
        this.eq_exp = eq_exp;
    }

    @Override
    public void print() throws IOException {
        rel_exp.print();
        printAstName(EqExp.class);
        if(op_token != null){
            op_token.print();
            eq_exp.print();
        }
    }

    @Override
    public void visit() {
        rel_exp.visit();
        if(eq_exp!=null){
            eq_exp.visit();
        }
    }


    @Override
    public int getLineNumber() {
        if(op_token==null){
            return rel_exp.getLineNumber();
        }
        return eq_exp.getLineNumber();
    }

    @Override
    public SymbolType getSymbolType() {
        return ((RelExp)rel_exp).getSymbolType();
    }
}
