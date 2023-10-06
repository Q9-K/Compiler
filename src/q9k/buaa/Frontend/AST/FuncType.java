package q9k.buaa.Frontend.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;

public class FuncType implements Syntax{
    private Token func_type;

    public FuncType(Token func_type) {
        this.func_type = func_type;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {
        if(!func_type.getTokenType().equals(TokenType.VOIDTK)&&!func_type.getTokenType().equals(TokenType.INTTK)){
            ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, func_type.getLine_number()));
        }
    }
}
