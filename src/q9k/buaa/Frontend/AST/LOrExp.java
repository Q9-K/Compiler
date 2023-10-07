package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.INIT.Output;

import java.io.IOException;

public class LOrExp implements Syntax{
    private Syntax l_and_exp;
    private Token or_token;
    private Syntax l_or_exp;

    public LOrExp(Syntax l_and_exp, Token or_token, Syntax l_or_exp) {
        this.l_and_exp = l_and_exp;
        this.or_token = or_token;
        this.l_or_exp = l_or_exp;
        handleError();
    }

    @Override
    public void print() throws IOException {
        l_and_exp.print();
        print_ast_name(LOrExp.class);
        if(or_token != null){
            or_token.print();
            l_or_exp.print();
        }
   /*     else {
            print_ast_name(LOrExp.class);
        }*/
    }

    @Override
    public void handleError() {

    }
}
