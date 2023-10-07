package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.INIT.Output;

import java.io.IOException;

public class RelExp implements Syntax{
    private Syntax add_exp;
    private Token op_token;
    private Syntax rel_exp;


    public RelExp(Syntax add_exp, Token op_token, Syntax rel_exp) {
        this.add_exp = add_exp;
        this.op_token = op_token;
        this.rel_exp = rel_exp;
        handleError();
    }

    @Override
    public void print() throws IOException {
        add_exp.print();
        print_ast_name(RelExp.class);
        if(op_token != null){
            op_token.print();
            rel_exp.print();
        }
//        else {
//            print_ast_name(RelExp.class);
//        }
    }

    @Override
    public void handleError() {

    }
}
