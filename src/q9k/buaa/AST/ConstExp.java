package q9k.buaa.AST;

import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class ConstExp implements Syntax {
    private Syntax add_exp;
    private SymbolTable symbolTable;
    public ConstExp(Syntax add_exp) {
        this.add_exp = add_exp;
    }

    @Override
    public void print() throws IOException {
        add_exp.print();
        printAstName(ConstExp.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        add_exp.visit();
    }


    @Override
    public int getLineNumber() {
        return add_exp.getLineNumber();
    }


    @Override
    public String toString() {
        return add_exp.toString();
    }

}
