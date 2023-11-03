package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class BlockStmt implements Stmt{
    private Syntax block;
    private SymbolTable symbolTable;

    public BlockStmt(Syntax block) {
        this.block = block;
    }

    @Override
    public void print() throws IOException {
        block.print();
        printAstName(Stmt.class);

    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        SymbolTable.changeTo(SymbolTable.getCurrent().createSymbolTable());
        block.visit();
        SymbolTable.changeToFather();
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

    @Override
    public String toString() {
        return block.toString();
    }


}
