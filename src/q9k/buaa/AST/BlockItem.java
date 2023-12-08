package q9k.buaa.AST;

import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;

import java.io.IOException;

public class BlockItem implements Syntax {
    private Syntax decl;
    private Syntax stmt;
    private SymbolTable symbolTable;
    

    public BlockItem(Syntax decl, Syntax stmt) {
        this.decl = decl;
        this.stmt = stmt;
    }

    @Override
    public void print() throws IOException {
        if (decl != null) {
            decl.print();
        } else {
            stmt.print();
        }
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        if (decl != null) {
            decl.visit();
        } else {
            stmt.visit();
        }
    }


    @Override
    public int getLineNumber() {
        if (decl != null) {
            return decl.getLineNumber();
        }
        return stmt.getLineNumber();
    }

    @Override
    public Value generateIR() {
        if (decl != null) {
            return decl.generateIR();
        } else {
            return stmt.generateIR();
        }
    }


    @Override
    public String toString() {
        if (decl != null) {
            return decl.toString();
        }
        return stmt.toString();
    }

}
