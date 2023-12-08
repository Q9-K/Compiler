package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class UnaryOp implements Syntax {
    private Token op_token;
    private SymbolTable symbolTable;

    public UnaryOp(Token op_token) {
        this.op_token = op_token;
    }

    @Override
    public void print() throws IOException {
        op_token.print();
        printAstName(UnaryOp.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
    }

    @Override
    public int getLineNumber() {
        return op_token.getLineNumber();
    }

    @Override
    public Value generateIR() {
        return null;
    }


    @Override
    public String toString() {
        return op_token.toString();
    }

    public Token getOp_token(){
        return this.op_token;
    }
}
