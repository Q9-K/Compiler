package q9k.buaa.Frontend.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;

import java.io.IOException;

public class ForStmt implements Syntax{
    private Syntax l_val;
    private Token assign_token;
    private Syntax exp;

    public ForStmt(Syntax l_val, Token assign_token, Syntax exp) {
        this.l_val = l_val;
        this.assign_token = assign_token;
        this.exp = exp;
    }

    @Override
    public void print() throws IOException {
        l_val.print();
        assign_token.print();
        exp.print();
        print_ast_name(ForStmt.class);
    }

    @Override
    public void handleError() {
        if(!assign_token.getTokenType().equals(TokenType.ASSIGN)){
            ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, assign_token.getLine_number()));
        }
    }
}



