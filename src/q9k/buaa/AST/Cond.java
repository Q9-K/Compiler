package q9k.buaa.AST;

import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class Cond implements Syntax {
    private Syntax l_or_exp;
    

    public Cond(Syntax l_or_exp) {
        this.l_or_exp = l_or_exp;
    }

    @Override
    public void print() throws IOException {
        l_or_exp.print();
        printAstName(Cond.class);
    }

    @Override
    public void visit() {
        
        l_or_exp.visit();
    }


    @Override
    public int getLineNumber() {
        return l_or_exp.getLineNumber();
    }

    @Override
    public Value generateIR() {
        return l_or_exp.generateIR();
    }


    @Override
    public String toString() {
        return l_or_exp.toString();
    }

}
