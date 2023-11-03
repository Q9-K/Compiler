package q9k.buaa.AST;

import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class IntConst implements Syntax {

    private Token intcon_token;
    private SymbolTable symbolTable;

    public IntConst(Token intcon_token) {
        this.intcon_token = intcon_token;
    }

    @Override
    public void print() throws IOException {
        intcon_token.print();
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
    }

    @Override
    public int getLineNumber() {
        return intcon_token.getLineNumber();
    }

    @Override
    public String toString() {
        return intcon_token.toString();
    }


}
