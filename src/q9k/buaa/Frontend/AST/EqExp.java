package q9k.buaa.Frontend.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;
import q9k.buaa.INIT.Output;

import java.io.IOException;

public class EqExp implements Syntax{
    private Syntax rel_exp;
    private Token op_token ;
    private Syntax eq_exp ;

    public EqExp(Syntax rel_exp, Token op_token, Syntax eq_exp) {
        this.rel_exp = rel_exp;
        this.op_token = op_token;
        this.eq_exp = eq_exp;
        handleError();
    }

    @Override
    public void print() throws IOException {
        rel_exp.print();
        print_ast_name(EqExp.class);
        if(op_token != null){
            op_token.print();
            eq_exp.print();
        }
//        else {
//            print_ast_name(EqExp.class);
//        }
    }

    @Override
    public void handleError() {
        if(op_token!=null){
            if(!op_token.getTokenType().equals(TokenType.NEQ)||!op_token.getTokenType().equals(TokenType.EQL)){
                ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, op_token.getLine_number()));
            }
        }
    }
}
