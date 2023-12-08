package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;

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
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        SymbolTableFactory.getInstance().setCurrent(SymbolTableFactory.getInstance().createSymbolTable());
        block.visit();
        SymbolTableFactory.getInstance().setCurrent(SymbolTableFactory.getInstance().getCurrent().getFather());
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

    @Override
    public Value generateIR() {
        return block.generateIR();
    }

    @Override
    public String toString() {
        return block.toString();
    }


}
