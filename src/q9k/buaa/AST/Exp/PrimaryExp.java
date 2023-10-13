package q9k.buaa.AST.Exp;

import q9k.buaa.AST.LVal;
import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class PrimaryExp implements Syntax,Type {

    private Token lparent;
    private Syntax exp;
    private Token rparent;
    private Syntax l_val;
    private Syntax number;

    public PrimaryExp(Token lparent, Syntax exp, Token rparent, Syntax l_val, Syntax number) {
        this.lparent = lparent;
        this.exp = exp;
        this.rparent = rparent;
        this.l_val = l_val;
        this.number = number;
    }

    @Override
    public void print() throws IOException {
        if(exp != null){
            lparent.print();
            exp.print();
            rparent.print();
        }
        else if(l_val != null){
            l_val.print();
        }
        else if(number!=null){
            number.print();
        }
        printAstName(PrimaryExp.class);
    }

    @Override
    public void visit() {
        if(exp != null){
            exp.visit();
        }
        else if(l_val != null){
            l_val.visit();
        }
        else if(number!=null){
            number.visit();
        }
    }

    @Override
    public int getLineNumber() {

        if(exp != null){
            return exp.getLineNumber();
        }
        else if(l_val != null){
            return l_val.getLineNumber();
        }
        else{
            return number.getLineNumber();
        }
    }

    @Override
    public SymbolType getSymbolType() {
        if(exp != null){
            return ((Exp)exp).getSymbolType();
        }
        else if(l_val != null){
            return ((LVal)l_val).getSymbolType();
        }
        else{
            return SymbolType.VAR;
        }
    }
}
