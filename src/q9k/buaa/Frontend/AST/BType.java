package q9k.buaa.Frontend.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;

public class BType implements Syntax {
    private Token int_token;
    public BType(Token int_token){
        this.int_token = int_token;
    }
    @Override
    public void print() {

    }

    @Override
    public void handleError() {
        if(!int_token.getTokenType().equals(TokenType.INTTK)){
            ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, int_token.getLine_number()));
        }
    }
}
