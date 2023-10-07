package q9k.buaa.Frontend.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;
import q9k.buaa.INIT.Output;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.List;

public class ConstDecl implements Syntax {
    private Token const_token;
    private Syntax b_type;
    private Syntax const_def;
    private List<Tuple<Token, Syntax>> list;
    private Token semicn_token;

    public ConstDecl(Token const_token, Syntax b_type, Syntax const_def, List<Tuple<Token, Syntax>> list, Token semicn_token) {
        this.const_token = const_token;
        this.b_type = b_type;
        this.const_def = const_def;
        this.list = list;
        this.semicn_token = semicn_token;
        handleError();
    }

    @Override
    public void print() throws IOException {
        const_token.print();
        b_type.print();
        const_def.print();
        for(Tuple<Token, Syntax> item : list){
            item.getFirst().print();
            item.getSecond().print();
        }
        semicn_token.print();
        print_ast_name(ConstDecl.class);
    }

    @Override
    public void handleError() {
        if(!const_token.getTokenType().equals(TokenType.CONSTTK)){
            ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, const_token.getLine_number()));
        }
    }
}
