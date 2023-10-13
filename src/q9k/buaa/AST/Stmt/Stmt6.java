package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class Stmt6 implements Stmt{
    private Syntax block;

    public Stmt6(Syntax block) {
        this.block = block;
    }

    @Override
    public void print() throws IOException {
        block.print();
        printAstName(Stmt.class);

    }

    @Override
    public void visit() {
        SymbolTable current = SymbolTable.getCurrent();
        SymbolTable symbolTable = new SymbolTable();
        SymbolTable.changeToTable(symbolTable);
        block.visit();
        SymbolTable.changeToTable(current);
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

}
