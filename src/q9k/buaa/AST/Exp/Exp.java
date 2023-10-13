package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class Exp implements Syntax,Type {
    private Syntax add_exp;


    public Exp(Syntax add_exp) {
        this.add_exp = add_exp;
    }

    @Override
    public void print() throws IOException {
        add_exp.print();
        printAstName(Exp.class);
    }

    @Override
    public void visit() {
        add_exp.visit();
    }



    @Override
    public int getLineNumber() {
        return add_exp.getLineNumber();
    }

    @Override
    public SymbolType getSymbolType() {
        return ((AddExp)add_exp).getSymbolType();
    }
}
