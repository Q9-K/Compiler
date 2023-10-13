package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class LAndExp implements Syntax,Type {

    private Syntax eq_exp;
    private Token and_token;
    private Syntax l_and_exp;


    public LAndExp(Syntax eq_exp, Token and_token, Syntax l_and_exp) {
        this.eq_exp = eq_exp;
        this.and_token = and_token;
        this.l_and_exp = l_and_exp;
    }

    @Override
    public void print() throws IOException {
        eq_exp.print();
        printAstName(LAndExp.class);
        if(and_token != null){
            and_token.print();
            l_and_exp.print();
        }
//        else {
//            print_ast_name(LAndExp.class);
//        }
    }

    @Override
    public void visit() {
        eq_exp.visit();
        if(l_and_exp!=null){
            l_and_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if(and_token==null){
            return eq_exp.getLineNumber();
        }
        return l_and_exp.getLineNumber();
    }

    @Override
    public SymbolType getSymbolType() {
        return ((EqExp)eq_exp).getSymbolType();
    }
}
