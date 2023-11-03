package q9k.buaa.AST;

import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class Number implements Syntax {

    private Syntax int_const;
    private SymbolTable symbolTable;

    public Number(Syntax int_const) {
        this.int_const = int_const;
    }

    @Override
    public void print() throws IOException {
        int_const.print();
        printAstName(Number.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
    }

    @Override
    public int getLineNumber() {
        return int_const.getLineNumber();
    }

    @Override
    public String toString() {
        return int_const.toString();
    }


}
