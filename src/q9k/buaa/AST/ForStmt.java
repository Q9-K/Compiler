package q9k.buaa.AST;

import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class ForStmt implements Syntax {
    private Syntax l_val;
    private Token assign_token;
    private Syntax exp;
    private SymbolTable symbolTable;

    public ForStmt(Syntax l_val, Token assign_token, Syntax exp) {
        this.l_val = l_val;
        this.assign_token = assign_token;
        this.exp = exp;
    }

    @Override
    public void print() throws IOException {
        l_val.print();
        assign_token.print();
        exp.print();
        printAstName(ForStmt.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
    }

    @Override
    public int getLineNumber() {
        return l_val.getLineNumber();
    }


    @Override
    public String toString() {
        return l_val.toString()+assign_token.toString()+exp.toString();
    }
}



