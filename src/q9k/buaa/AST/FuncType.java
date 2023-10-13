package q9k.buaa.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class FuncType implements Syntax {
    private Token func_type;

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

    }

    @Override
    public int getLineNumber() {
        return func_type.getLineNumber();
    }

    public SymbolType getFunc_type(){
        if(func_type.getTokenType().equals(TokenType.VOIDTK)){
            return SymbolType.VOID;
        }
        else{
            return SymbolType.VAR;
        }
    }

}
