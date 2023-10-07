package q9k.buaa.Frontend.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FuncFParam implements Syntax{
    private Syntax b_type;
    private Syntax ident;
    private Token lbrack = null;
    private Token rbrack = null;
    private List<Triple<Token, Syntax, Token>> list;

    public FuncFParam(Syntax b_type, Syntax ident, Token lbrack, Token rbrack, List<Triple<Token, Syntax, Token>> list) {
        this.b_type = b_type;
        this.ident = ident;
        this.lbrack = lbrack;
        this.rbrack = rbrack;
        this.list = list;
        handleError();
    }

    @Override
    public void print() throws IOException {
        b_type.print();
        ident.print();
        if(lbrack!=null){
            lbrack.print();
            rbrack.print();
        }
        for(Triple<Token, Syntax, Token> item : list){
            item.getFirst().print();
            item.getSecond().print();
            item.getThird().print();
        }
        print_ast_name(FuncFParam.class);
    }

    @Override
    public void handleError() {
        if(lbrack!=null){
            if(!(lbrack.getTokenType().equals(TokenType.LBRACK)&&rbrack.getTokenType().equals(TokenType.RBRACK))){
                ErrorHandler.getInstance().addError(new Error(ErrorType.MISSINGRBRACK, rbrack.getLine_number()));
            }
        }
    }
}
