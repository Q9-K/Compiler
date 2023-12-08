package q9k.buaa.AST;

import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class FormatString implements Syntax {
    private Token strcon_token;
    private SymbolTable symbolTable;
    

    public FormatString(Token strcon_token) {
        this.strcon_token = strcon_token;
    }

    @Override
    public void print() throws IOException {
        strcon_token.print();
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
    }

    @Override
    public int getLineNumber() {
        return strcon_token.getLineNumber();
    }

    @Override
    public Value generateIR() {
        return null;
    }

    @Override
    public String toString() {
        return strcon_token.toString();
    }



}
