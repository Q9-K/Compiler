package q9k.buaa.AST;

import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class Ident implements Syntax {
    private Token ident_token;
    

    public Ident(Token ident_token) {
        this.ident_token = ident_token;
    }

    @Override
    public void print() throws IOException {
        ident_token.print();
    }
    //TODO:
    //identifier → identifier-nondigit
    //| identifier identifier-nondigit
    //| identifier digit
    //其中，identifier-nondigit为下划线或大小写字母，digit为0到9的数字
    @Override
    public void visit() {
        
    }

    @Override
    public int getLineNumber() {
        return ident_token.getLineNumber();
    }

    @Override
    public Value generateIR() {
        return new Value(this.toString(), IntegerType.i32);
    }

    @Override
    public String toString() {
        return ident_token.toString();
    }
}
