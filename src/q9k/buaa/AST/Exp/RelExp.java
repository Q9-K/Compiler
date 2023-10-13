package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class RelExp implements Syntax,Type {
    private Syntax add_exp;
    private Token op_token;
    private Syntax rel_exp;


    public RelExp(Syntax add_exp, Token op_token, Syntax rel_exp) {
        this.add_exp = add_exp;
        this.op_token = op_token;
        this.rel_exp = rel_exp;
    }

    @Override
    public void print() throws IOException {
        add_exp.print();
        printAstName(RelExp.class);
        if(op_token != null){
            op_token.print();
            rel_exp.print();
        }
    }

    @Override
    public void visit() {
        add_exp.visit();
        if(rel_exp!=null){
            rel_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

    @Override
    public SymbolType getSymbolType() {
        return ((AddExp)add_exp).getSymbolType();
    }
}
