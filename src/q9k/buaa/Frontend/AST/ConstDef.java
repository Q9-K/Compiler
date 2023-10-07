package q9k.buaa.Frontend.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;
import q9k.buaa.INIT.Output;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstDef implements Syntax {
    private Syntax ident;
    private List<Triple<Token,Syntax,Token>> list = new ArrayList<>();
    private Token assign_token;
    private Syntax const_init_val;


    public ConstDef(Syntax ident, List<Triple<Token, Syntax, Token>> list, Token assign_token, Syntax const_init_val) {
        this.ident = ident;
        this.list = list;
        this.assign_token = assign_token;
        this.const_init_val = const_init_val;
        handleError();
    }

    @Override
    public void print() throws IOException {
        ident.print();
        for(Triple<Token, Syntax, Token> item : list){
            item.getFirst().print();
            item.getSecond().print();
            item.getThird().print();
        }
        assign_token.print();
        const_init_val.print();
        print_ast_name(ConstDef.class);
    }

    @Override
    public void handleError() {
        if(!assign_token.getTokenType().equals(TokenType.ASSIGN)){
            ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, assign_token.getLine_number()));
        }
    }
}
