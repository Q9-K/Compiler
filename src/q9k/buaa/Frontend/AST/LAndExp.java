package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.INIT.Output;

import java.io.IOException;

public class LAndExp implements Syntax{

    private Syntax eq_exp;
    private Token and_token;
    private Syntax l_and_exp;


    public LAndExp(Syntax eq_exp, Token and_token, Syntax l_and_exp) {
        this.eq_exp = eq_exp;
        this.and_token = and_token;
        this.l_and_exp = l_and_exp;
        handleError();
    }

    @Override
    public void print() throws IOException {
        eq_exp.print();
        print_ast_name(LAndExp.class);
        if(and_token != null){
            and_token.print();
            l_and_exp.print();
        }
//        else {
//            print_ast_name(LAndExp.class);
//        }
    }

    @Override
    public void handleError() {

    }
}
