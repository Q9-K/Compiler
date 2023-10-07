package q9k.buaa.Frontend.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class FormatString implements Syntax{
    private Token strcon_token;

    public FormatString(Token strcon_token) {
        this.strcon_token = strcon_token;
        handleError();
    }

    @Override
    public void print() throws IOException {
        strcon_token.print();
    }

    @Override
    public void handleError() {
        if(!strcon_token.getContent().matches("\"(%d|[ -!]|[(-~]|\\n)*\"")){
            ErrorHandler.getInstance().addError(new Error(ErrorType.ILLEGALSYMBOL, strcon_token.getLine_number()));
        }
    }
}
