package q9k.buaa.Frontend.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;

public class AddExp implements Syntax{
    private Syntax mul_exp;
    private Token op_token;
    private Syntax add_exp;

    public AddExp(Syntax mul_exp, Token op_token, Syntax add_exp) {
        this.mul_exp = mul_exp;
        this.op_token = op_token;
        this.add_exp = add_exp;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {
        if(!(op_token.getTokenType().equals(TokenType.PLUS)||op_token.getTokenType().equals(TokenType.MINU))){
            ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, op_token.getLine_number()));
        }
    }
}
