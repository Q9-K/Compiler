package q9k.buaa.AST.Function;

import q9k.buaa.AST.Syntax;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;
import java.io.IOException;

public class FuncType implements Syntax {
    private Token func_type;
    private SymbolTable symbolTable;

    public FuncType(Token func_type) {
        this.func_type = func_type;
    }

    @Override
    public void print() throws IOException {
        func_type.print();
        printAstName(FuncType.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
    }

    @Override
    public int getLineNumber() {
        return func_type.getLineNumber();
    }

    @Override
    public Value genIR() {
        return null;
    }

    @Override
    public String toString() {
        return func_type.toString();
    }
}
