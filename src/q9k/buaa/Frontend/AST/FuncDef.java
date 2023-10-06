package q9k.buaa.Frontend.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;

public class FuncDef implements Syntax {

    private Syntax func_type;
    private Syntax ident;
    private Token lparent;
    private Syntax func_f_params;
    private Token rparent;
    private Syntax block;

    public FuncDef(Syntax func_type, Syntax ident, Token lparent, Syntax func_f_params, Token rparent, Syntax block) {
        this.func_type = func_type;
        this.ident = ident;
        this.lparent = lparent;
        this.func_f_params = func_f_params;
        this.rparent = rparent;
        this.block = block;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {
        if(!(lparent.getTokenType().equals(TokenType.LPARENT)&&rparent.getTokenType().equals(TokenType.RPARENT))){
            ErrorHandler.getInstance().addError(new Error(ErrorType.MISSINGRPARENT, rparent.getLine_number()));
        }
    }
}
