package q9k.buaa.AST;

import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class BType implements Syntax {
    private Token type_token;
    private SymbolTable symbolTable;
    
    public BType(Token type_token){
        this.type_token = type_token;
    }
    @Override
    public void print() throws IOException {
        type_token.print();
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
    }



    @Override
    public int getLineNumber() {
        return type_token.getLineNumber();
    }

    @Override
    public String toString() {
        return type_token.toString();
    }

    @Override
    public Value genIR() {
        return null;
    }
}
