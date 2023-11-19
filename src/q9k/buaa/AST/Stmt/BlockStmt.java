package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.BasicBlock;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class BlockStmt implements Stmt{
    private Syntax block;
    

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
        
        SymbolTable.changeTo(SymbolTable.getCurrent().createSymbolTable());
        block.visit();
        SymbolTable.changeToFather();
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
